package me.xiaowei.modules.system.rest;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.xiaowei.aop.log.Log;
import me.xiaowei.config.DataScope;
import me.xiaowei.domain.VerificationCode;
import me.xiaowei.modules.pes.domain.T_student;
import me.xiaowei.modules.pes.domain.T_teacher;
import me.xiaowei.modules.pes.repository.T_studentDAO;
import me.xiaowei.modules.pes.repository.T_teacherDAO;
import me.xiaowei.modules.system.domain.Dept;
import me.xiaowei.modules.system.domain.Job;
import me.xiaowei.modules.system.domain.Role;
import me.xiaowei.modules.system.domain.User;
import me.xiaowei.exception.BadRequestException;
import me.xiaowei.modules.system.domain.vo.UserPassVo;
import me.xiaowei.modules.system.repository.UserRepository;
import me.xiaowei.modules.system.service.DeptService;
import me.xiaowei.modules.system.service.RoleService;
import me.xiaowei.modules.system.service.dto.RoleSmallDTO;
import me.xiaowei.modules.system.service.dto.UserQueryCriteria;
import me.xiaowei.service.VerificationCodeService;
import me.xiaowei.modules.system.service.UserService;
import me.xiaowei.utils.ElAdminConstant;
import me.xiaowei.utils.EncryptUtils;
import me.xiaowei.utils.PageUtil;
import me.xiaowei.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Api(tags = "系统：用户管理")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final DataScope dataScope;

    private final DeptService deptService;

    private final RoleService roleService;

    private final VerificationCodeService verificationCodeService;

    public UserController(UserService userService, DataScope dataScope, DeptService deptService, RoleService roleService, VerificationCodeService verificationCodeService, UserRepository userRepository) {
        this.userService = userService;
        this.dataScope = dataScope;
        this.deptService = deptService;
        this.roleService = roleService;
        this.verificationCodeService = verificationCodeService;
        this.userRepository = userRepository;
    }

    @Log("导出用户数据")
    @ApiOperation("导出用户数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('user:list')")
    public void download(HttpServletResponse response, UserQueryCriteria criteria) throws IOException {
        userService.download(userService.queryAll(criteria), response);
    }

    @Log("查询用户")
    @ApiOperation("查询用户")
    @GetMapping
    @PreAuthorize("@el.check('user:list')")
    public ResponseEntity getUsers(UserQueryCriteria criteria, Pageable pageable) {
        Set<Long> deptSet = new HashSet<>();
        Set<Long> result = new HashSet<>();
        if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
            deptSet.add(criteria.getDeptId());
            deptSet.addAll(dataScope.getDeptChildren(deptService.findByPid(criteria.getDeptId())));
        }
        // 数据权限
        Set<Long> deptIds = dataScope.getDeptIds();
        // 查询条件不为空并且数据权限不为空则取交集
        if (!CollectionUtils.isEmpty(deptIds) && !CollectionUtils.isEmpty(deptSet)) {
            // 取交集
            result.addAll(deptSet);
            result.retainAll(deptIds);
            // 若无交集，则代表无数据权限
            criteria.setDeptIds(result);
            if (result.size() == 0) {
                return new ResponseEntity<>(PageUtil.toPage(null, 0), HttpStatus.OK);
            } else return new ResponseEntity<>(userService.queryAll(criteria, pageable), HttpStatus.OK);
            // 否则取并集
        } else {
            result.addAll(deptSet);
            result.addAll(deptIds);
            criteria.setDeptIds(result);
            return new ResponseEntity<>(userService.queryAll(criteria, pageable), HttpStatus.OK);

        }
    }

    @Log("新增用户")
    @ApiOperation("新增用户")
    @PostMapping
    @PreAuthorize("@el.check('user:add')")
    public ResponseEntity create(@Validated @RequestBody User resources) {
        checkLevel(resources);
        return new ResponseEntity<>(userService.create(resources), HttpStatus.CREATED);
    }

    @Autowired
    T_teacherDAO t_teacherDao;
    @Autowired
    T_studentDAO t_studentDao;
    private final UserRepository userRepository;
    @Log("新增用户：同步教师和用户表")
    @ApiOperation("新增用户：同步教师和用户表")
    @GetMapping("/sync")
    @PreAuthorize("@el.check('user:add')")
    public ResponseEntity syncCreate() {

        /**1.得到教室列表和学生列表,初始化新增set
         * 2.查看是否存在，不存在加入新增set
         * 3.遍历set，创建用户
         * **/
        List<T_teacher> teachers = new LinkedList<>();
        List<T_student> students = new LinkedList<>();
        HashSet<String> id_list = new HashSet<>();
        HashSet<String> num_list = new HashSet<>();
        students = t_studentDao.findAll();
        teachers = t_teacherDao.findAll();
        HashMap<String, String> log = new HashMap<>();  //错误信息放在这
        ArrayList copy = new ArrayList();               // 重复项放在这里
        for (T_teacher t : teachers) {
            if (t.getTeacherId() != "" && t.getTeacherId() != null && !t.getTeacherName().trim().equals("")) {
                if (userRepository.findByUsername(t.getTeacherId()) == null) {
                    id_list.add(t.getTeacherId());
                } else {
                    copy.add(t.getTeacherId());
                }
            }
        }
        for (T_student s : students) {
            if (!s.getStuNum().trim().equals("") && s.getStuNum() != null && !s.getStuName().trim().equals("")) {
                if (userRepository.findByUsername(s.getStuNum()) == null) {
                    num_list.add(s.getStuNum());
                } else {
                    copy.add(s.getStuName());
                }
            }
        }
        if (!id_list.isEmpty()) {
            for (String s : id_list) {
                try {
                    User user = getNewUser(s, 12L, 14L, 2L,1);
                    userService.create(user);
                } catch (Exception e) {
                    id_list.remove(s);
                    log.put(s, e.toString());
                }
            }
        }
        if (!num_list.isEmpty()) {
            for (String s : num_list) {
                try {
                    User user = getNewUser(s, 12L, 14L, 2L,0);
                    userService.create(user);
                } catch (Exception e) {
                    num_list.remove(s);
                    log.put(s, e.toString());
                }
            }
        }
        id_list.addAll(num_list);
        if (id_list.isEmpty()) {
            JSONObject data = new JSONObject();
            data.put("msg","没有要导入的信息");
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        JSONObject data = new JSONObject();
        int count = teachers.size() + students.size();
        data.put("Count", count);
        data.put("FinishedCount", count - log.size());
        data.put("Finished", id_list);
        data.put("Failed", log.size());
        data.put("ErrorInfo", log);
        data.put("Duplicate", copy);
        if (!log.isEmpty()) {
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        //checkLevel(resources);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }


    public User getNewUser(String s, Long deptId, Long jobId, Long roleId, int flag) {
        User user = new User();
        Dept dept = new Dept();
        Role role = new Role();
        Job job = new Job();
        Set<Role> roles = new HashSet<>();
        dept.setId(deptId);  //12,北京石油化工学院。
        job.setId(jobId);      //14,学生。13，教职工。
        role.setId(roleId);
        roles.add(role); //2，普通用户。
        user.setEnabled(Boolean.TRUE);
        user.setRoles(roles);
        user.setDept(dept);
        user.setJob(job);
        user.setUsername(s);
        if (flag == 0) {
            user.setEmail(t_studentDao.findByStuNum(s).getStuEmail());
            user.setPhone(t_studentDao.findByStuNum(s).getStuTel());
            user.setPassword(t_studentDao.findByStuNum(s).getStuPassword());
        } else {
            user.setPhone("12345678987");
            user.setEmail(s + "@bitp.edu.cn");
            user.setPassword(t_teacherDao.findByTeacherId(s).getTeacherPassword());
        }

        return  user;
    }

    @Log("修改用户")
    @ApiOperation("修改用户")
    @PutMapping
    @PreAuthorize("@el.check('user:edit')")
    public ResponseEntity update(@Validated(User.Update.class) @RequestBody User resources) {
        checkLevel(resources);
        userService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @ApiOperation("删除用户")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@el.check('user:del')")
    public ResponseEntity delete(@PathVariable Long id) {
        Integer currentLevel = Collections.min(roleService.findByUsers_Id(SecurityUtils.getUserId()).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList()));
        Integer optLevel = Collections.min(roleService.findByUsers_Id(id).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList()));

        if (currentLevel > optLevel) {
            throw new BadRequestException("角色权限不足");
        }
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation("修改密码")
    @PostMapping(value = "/updatePass")
    public ResponseEntity updatePass(@RequestBody UserPassVo user) {
        UserDetails userDetails = SecurityUtils.getUserDetails();
        if (!userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getOldPass()))) {
            throw new BadRequestException("修改失败，旧密码错误");
        }
        if (userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getNewPass()))) {
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        userService.updatePass(userDetails.getUsername(), EncryptUtils.encryptPassword(user.getNewPass()));
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation("修改头像")
    @PostMapping(value = "/updateAvatar")
    public ResponseEntity updateAvatar(@RequestParam MultipartFile file) {
        userService.updateAvatar(file);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("修改邮箱")
    @ApiOperation("修改邮箱")
    @PostMapping(value = "/updateEmail/{code}")
    public ResponseEntity updateEmail(@PathVariable String code, @RequestBody User user) {
        UserDetails userDetails = SecurityUtils.getUserDetails();
        if (!userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getPassword()))) {
            throw new BadRequestException("密码错误");
        }
        VerificationCode verificationCode = new VerificationCode(code, ElAdminConstant.RESET_MAIL, "email", user.getEmail());
        verificationCodeService.validated(verificationCode);
        userService.updateEmail(userDetails.getUsername(), user.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
     *
     * @param resources /
     */
    private void checkLevel(User resources) {
        Integer currentLevel = Collections.min(roleService.findByUsers_Id(SecurityUtils.getUserId()).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList()));
        Integer optLevel = roleService.findByRoles(resources.getRoles());
        if (currentLevel > optLevel) {
            throw new BadRequestException("角色权限不足");
        }
    }
}
