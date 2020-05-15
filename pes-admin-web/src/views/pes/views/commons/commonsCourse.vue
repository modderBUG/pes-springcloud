<template>
  <div class="app-container">
    <div class="head-container">
      <!--      <el-input v-model="query.value" clearable placeholder="全表模糊搜索" style="width: 200px;" class="filter-item"-->
      <!--                @keyup.enter.native="toQuery"/>-->
      <el-divider content-position="left"><a style="font-family:sans-serif;font-size:large;color: #303133;">点击选择周数</a></el-divider>
<!--      <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="">搜索</el-button>-->
      <!-- 导出 -->
      <div style="display: inline-block;">
        <!--        <el-button-->
        <!--          :loading="downloadLoading"-->
        <!--          size="mini"-->
        <!--          class="filter-item"-->
        <!--          type="warning"-->
        <!--          icon="el-icon-download"-->
        <!--          @click="download">导出-->
        <!--        </el-button>-->
      </div>
    </div>
    <!--便利周数-->
    <div>
      <el-radio-group v-model="radio1" @change="handlerSelect(radio1)">
        <el-radio-button v-for="(item,index) in this.termForm.termWeeks" :label="item">第{{item}}周</el-radio-button>
      </el-radio-group>
    </div>
    <el-divider content-position="left"><a style="font-family:sans-serif;font-size:large;color: #303133;">第{{radio1}}周课程信息</a>
    </el-divider>
    <el-table
      :data="showData"
      style="width: 100%"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-table
              :data="props.row.tableData2"
              style="width: 100%"
              :row-class-name="tableRowClassName" border>
              <el-table-column prop="deskId" label="桌号" width="180"></el-table-column>
              <el-table-column prop="stuNum" label="学号" width="180"></el-table-column>
              <el-table-column prop="stuName" label="姓名" width="180"></el-table-column>
              <el-table-column prop="stuClass" label="班级"></el-table-column>
              <el-table-column prop="attendance" label="考勤"></el-table-column>
              <el-table-column prop="finTime" label="完成"></el-table-column>
              <el-table-column prop="testFlag" label="预习"></el-table-column>
              <el-table-column prop="score" label="成绩"></el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button class="el-button el-button--primary el-button--mini"
                             @click="handleEdit(scope.$index, scope.row,props.row,props.$index)"><i
                    class="el-icon-edit"></i>编辑
                  </el-button>
                  <!--                  <el-button-->
                  <!--                    size="mini"-->
                  <!--                    type="danger"-->
                  <!--                    @click="handleDelete(scope.$index, scope.row)">删除</el-button>-->
                </template>
              </el-table-column>
            </el-table>
          </el-form>
        </template>
      </el-table-column>
<!--      <el-table-column-->
<!--        label="实验ID"-->
<!--        width="100px"-->
<!--        prop="expId">-->
<!--      </el-table-column>-->
      <el-table-column
        label="实验名称"
        prop="expName">
      </el-table-column>
      <el-table-column
        label="星期"
        prop="timeWeek">
      </el-table-column>
      <el-table-column
        label="时段"
        prop="timeSchedule">
      </el-table-column>

    </el-table>

    <el-dialog ref="dialogedit" title="编辑修改" :visible.sync="dialogFormVisible" :modal-append-to-body="false"
               :append-to-body="true">
      <el-form :model="formDialog" :rules="Rules">
        <el-form-item label="桌号" :label-width="formLabelWidth" prop="deskId">
          <el-input v-model="formDialog.deskId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth" prop="stuName">
          <el-input v-model="formDialog.stuName" autocomplete="off" disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="学号" :label-width="formLabelWidth" prop="stuNum">
          <el-input v-model="formDialog.stuNum" autocomplete="off" disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="班级" :label-width="formLabelWidth" prop="stuClass">
          <el-input v-model="formDialog.stuClass" autocomplete="off"  disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="出勤" :label-width="formLabelWidth" prop="attendance">
          <el-select v-model="formDialog.attendance" placeholder="选择类别">
            <el-option label="出勤" value="出勤"></el-option>
            <el-option label="缺勤" value="缺勤"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="预习" :label-width="formLabelWidth" prop="testFlag">
          <el-input v-model="formDialog.testFlag" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分数" :label-width="formLabelWidth" prop="score">
          <el-input v-model="formDialog.score" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitFormDialog">确 定</el-button>
      </div>
    </el-dialog>

    <!--分页组件-->
    <!--    <el-pagination-->
    <!--      :total="total"-->
    <!--      :current-page="page + 1"-->
    <!--      style="margin-top: 8px;"-->
    <!--      layout="total, prev, pager, next, sizes"-->
    <!--      @size-change=""-->
    <!--      @current-change=""/>-->
  </div>
</template>

<script>

  //import initData from '@/mixins/initData'
  import {parseTime, downloadFile} from '@/utils/index'
  //import {del, downloadOnline} from '@/api/online'
  import {getTermInfo, edit} from '@/api/initsetting'
  import {
    getExperimentByWeek,
    getGradeByExp,
    findAllExperimentTime,
    getExperimentByTimeTimes,
    findExperimentByTimeTimes,
    getGradeByExpTime,
    getGradeByDto,
    editGradeByDto,
  } from "../../api/AdvanceQuery";

  export default {
    name: 'commons-course',
    //mixins: [initData],
    data() {
      return {
        labelwidth: "50px",
        total: '',

        delLoading: false,
        radio1: "1",
        page: '',
        showData: [],
        tableData: [],
        termForm: {},
        dialogTableVisible: false,
        dialogFormVisible: false,
        dialogVisible: false,
        formDialog: {
          attendance: '',
          deskId: '',
          score: '',
          stuClass: '',
          stuName: '',
          stuNum: '',
          testFlag: '',
        },
        Rules: {
          username: [{ required: true, trigger: 'blur', message: '用户名不能为空' }],
          password: [{ required: true, trigger: 'blur', message: '密码不能为空' }],
          code: [{ required: true, trigger: 'change', message: '验证码不能为空' }]
        },
        formLabelWidth: '120px',
        obj:{},

      }
    },
    created() {
      this.getTermInfo();
      this.getExperimentByWeek(1);
      // this.$nextTick(() => {
      //   this.init()
      // })
    },
    methods: {

      getTermInfo() {
        getTermInfo().then(data => {
          this.termForm = data[0];
        })
      },
      handleEdit(index, row, row2, index2) {
        this.dialogFormVisible = true;
        this.formDialog = row;

        // this.tableData[index2]

        this.obj = {
          expName: this.tableData[index2].expName,
          timeSchedule: this.tableData[index2].timeSchedule,
          timeTimes: this.tableData[index2].timeTimes,
          timeWeek: this.tableData[index2].timeWeek,
          attendance: row.attendance,
          deskId: row.deskId,
          score: row.score,
          stuClass: row.stuClass,
          stuName: row.stuName,
          stuNum: row.stuNum,
          testFlag: row.testFlag,
        }

      },

      submitFormDialog(){
        this.obj.attendance=this.formDialog.attendance
        this.obj.deskId=this.formDialog.deskId
        this.obj.score=this.formDialog.score
        this.obj.stuClass=this.formDialog.stuClass
        this.obj.stuName=this.formDialog.stuName
        this.obj.stuNum=this.formDialog.stuNum
        this.obj.testFlag=this.formDialog.testFlag
        this.dialogFormVisible = false;
        console.log(this.obj)

        editGradeByDto( this.obj).then(_=>{
          this.$notify({
            title: '写入成功',
            type: 'success',
            duration: 2500,
            //data: msg,
          })

          console.log(_)
        })
      },
      showDateHandler() {
        this.showData = [];
        for (var items in this.tableData) {
          this.showData.push(JSON.parse(JSON.stringify(this.tableData[items])))
          if (this.tableData[items].timeSchedule == "1") {
            this.showData[items].timeSchedule = "上午"
          } else if (this.tableData[items].timeSchedule == "2") {
            this.showData[items].timeSchedule = "下午"
          } else {
            this.showData[items].timeSchedule = "晚上"
          }

          this.showData[items].timeWeek = "星期" + this.showData[items].timeWeek;
        }
      },
      getExperimentByWeek(timeTimes) {
        // getExperimentByWeek(week).then(data=>{

        findExperimentByTimeTimes(timeTimes).then(data => {
          var list=[];
          for(var i = 0;i<=data.length-1;i++){
            var obj={
              "expName":data[i][0],
              "expId":data[i][1],
              "timeTimes":data[i][2],
              "timeWeek":data[i][3],
              "timeSchedule":data[i][4],
            }
            list.push(obj)
          }
          this.tableData = list;
          this.tableData.forEach(_ => {
            getGradeByDto(_).then(data => {
              _.tableData2 = data

              console.log(data)
              this.showDateHandler();
            })
          });
        })
      },

      handlerSelect(index) {
        //debug： 这里必须全部设置为空，因为tableData不加载会导致showData不刷新。从而导致shiowData遗留数据。
        this.tableData=[]
        this.showData=[]
        this.getExperimentByWeek(index)

        //console.log("index============="+index);
      },

      tableRowClassName({row, rowIndex}) {
        if (rowIndex === 1) {
          return 'warning-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return '';
      },

      parseTime,

      beforeInit() {
        this.url = 'auth/online'
        this.params = {page: this.page, size: this.size}
        if (this.query.value) {
          this.params['filter'] = this.query.value
        }
        return true
      },
      subDelete(index, key) {
        this.delLoading = true
        del(key).then(res => {
          this.delLoading = false
          this.$refs[index].doClose()
          this.dleChangePage()
          this.init()
          this.$notify({
            title: '踢出成功',
            type: 'success',
            duration: 2500
          })
        }).catch(err => {
          this.delLoading = false
          this.$refs[index].doClose()
          console.log(err.response.data.message)
        })
      },
      download() {
        this.beforeInit()
        this.downloadLoading = true
        downloadOnline(this.params).then(result => {
          downloadFile(result, '在线用户列表', 'xlsx')
          this.downloadLoading = false
        }).catch(() => {
          this.downloadLoading = false
        })
      }
    }
  }
</script>

<style scoped>
  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }

  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
