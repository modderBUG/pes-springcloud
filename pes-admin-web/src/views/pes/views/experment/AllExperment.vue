<template>

  <div style="margin: 20px 20px 20px 20px">
    <div class="head-container">
      <!-- 搜索 -->
      <el-input v-model="searchKey" clearable placeholder="输入名称或者描述搜索" style="width: 200px;" class="filter-item"
                @keyup.enter.native="toQuery"/>

      <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
      <!-- 新增 -->
      <div v-permission="['admin','roles:add']" style="display: inline-block;margin: 0px 2px;">
        <el-button
          class="filter-item"
          size="mini"
          type="primary"
          icon="el-icon-plus"
          @click="handleAdd">新增
        </el-button>
      </div>
      <!-- 导出 -->
      <div style="display: inline-block;">
        <el-button
          :loading="downloadLoading"
          size="mini"
          class="filter-item"
          type="warning"
          icon="el-icon-download"
          @click="download">导出
        </el-button>
      </div>
    </div>

    <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="17" style="margin: 0px 0px 0px 0px">
      <el-card class="box-card" shadow="never" style="max-width: 1000px">
        <div slot="header" class="clearfix">
          <span class="role-span"><h2>实验列表</h2></span>
        </div>

        <el-table
          :data="showData"
          style="width: 100%"
          :row-class-name="tableRowClassName">
          <el-table-column prop="expId" label="实验ID" width="100"></el-table-column>
          <el-table-column prop="expName" label="实验名称" width="200"></el-table-column>
          <el-table-column prop="expClass" label="类别" width="100"></el-table-column>
          <el-table-column prop="expLab" label="教室" width="100"></el-table-column>
          <el-table-column prop="expLimit" label="限选人数" width="100"></el-table-column>
          <el-table-column prop="expTerm" label="学期" width="100"></el-table-column>
          <el-table-column prop="expGroup" label="类别" width="100"></el-table-column>
          <el-table-column label="操作" fixed="right" width="150">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="handleEdit(scope.$index, scope.row)" icon="el-icon-edit"></el-button>
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)" icon="el-icon-delete-solid"></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-col>
    <!-- Form -->
<!--    <el-button type="text" @click="dialogFormVisible = true" >打开嵌套表单的 Dialog</el-button>-->
    <!--嵌套的dialog-->
    <el-dialog ref="dialogedit" title="编辑修改" :visible.sync="dialogFormVisible" :modal-append-to-body="false" :append-to-body="true" >
      <el-form :model="formDialog" :rules="rules">
        <el-form-item label="实验ID" :label-width="formLabelWidth" prop="expId">
          <el-input v-model="formDialog.expId" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="实验名称" :label-width="formLabelWidth" prop="expName">
          <el-input v-model="formDialog.expName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="实验类别" :label-width="formLabelWidth" prop="expClass">
          <el-select v-model="formDialog.expClass" placeholder="选择类别" >
            <el-option label="必修" value="1"></el-option>
            <el-option label="选修" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="教室" :label-width="formLabelWidth" prop="expLab">
          <el-input v-model="formDialog.expLab" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="限选人数" :label-width="formLabelWidth" prop="expLimit">
          <el-input v-model="formDialog.expLimit" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth" prop="expTerm">
          <el-select v-model="formDialog.expTerm" placeholder="请选择学期">
            <el-option label="第一学期" value="1"></el-option>
            <el-option label="第二学期" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类别" :label-width="formLabelWidth" prop="expGroup">
          <el-select v-model="formDialog.expGroup" placeholder="请选择文理">
            <el-option label="理科" value="1"></el-option>
            <el-option label="文科" value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="getExperment">确 定</el-button>
      </div>
    </el-dialog>
    <!--是否删除？-->
<!--    <el-button type="text" @click="dialogVisible = true">点击打开 Dialog</el-button>-->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :modal-append-to-body="false" :append-to-body="true"
      :before-close="handleClose">
      <span>这是一段信息</span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
    </el-dialog>
  </div>

</template>


<script>
  import {getAll, edit} from "../../api/ExpermentTable";

  export default {
    name:'AllExperment',
    data() {

      var checkNull = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('数据不能为空'));
        }

        // var checkAge = (rule, value, callback) => {
        //   if (!value) {
        //     return callback(new Error('年龄不能为空'));
        //   }
        //   setTimeout(() => {
        //     if (!Number.isInteger(value)) {
        //       callback(new Error('请输入数字值'));
        //     } else {
        //       if (value < 18) {
        //         callback(new Error('必须年满18岁'));
        //       } else {
        //         callback();
        //       }
        //     }
        //   }, 1000);
        // };
      };
      return {
        searchKey:'',
        tableData: [
          {
            id: '',
            expId: '',
            expName: '',
            expClass: '',
            expLab: '',
            expLimit: '',
            expGroup: '',
          }
        ],
        showData:[],

        dialogTableVisible: false,
        dialogFormVisible: false,
        dialogVisible: false,
        formDialog: {
          id: '',
          expId: '',
          expName: '',
          expClass: '',
          expLab: '',
          expLimit: '',
          expGroup: '',
        },
        formLabelWidth: '120px',
        rules: {
          expId: [
            { validator: checkNull, trigger: 'blur' }
          ],
          expName: [
            { validator: checkNull, trigger: 'blur' }
          ],
          expClass: [
            { validator: checkNull, trigger: 'blur' }
          ],
          expLab: [
            { validator: checkNull, trigger: 'blur' }
          ],
          expLimit: [
            { validator: checkNull, trigger: 'blur' }
          ],
          expTerm: [
            { validator: checkNull, trigger: 'blur' }
          ],
          expGroup: [
            { validator: checkNull, trigger: 'blur' }
          ],
        },



      }
    },
    methods: {

      showDateHandler(){
        this.showData=[];
        for (var items in this.tableData) {
          this.showData.push(JSON.parse(JSON.stringify(this.tableData[items])))
          if(this.tableData[items].expClass=="0"){
            this.showData[items].expClass="必修"
          }else{this.showData[items].expClass="选修"}
          if(this.tableData[items].expTerm=="1"){
            this.showData[items].expTerm="第一学期"
          }else{this.showData[items].expTerm="第二学期"}
          if(this.tableData[items].expGroup=="1"){
            this.showData[items].expGroup="理工科"
          }else{this.showData[items].expGroup="文科"}

        }
      },

      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            console.log("确认别执行")
            done();
            console.log("好滴")
          })
          .catch(_ => {
            console.log("取消被执行")
          });
        console.log("最先被执行")
      },
      handleEdit(index, row) {
        this.dialogFormVisible = true;
        this.formDialog = this.tableData[index];
      },
      handleAdd() {
        this.dialogFormVisible = true;
      },
      handleDelete(index, row) {

        this.$confirm('确认删除？'+row.expId+"----"+row.expName)
          .then(_ => {
            console.log("已删除");
            this.deleteRow(row.id)
          })
          .catch(_ => {

          });
      },

      deleteRow(id){

        this.$notify({
          title: '已删除',
          type: 'success',
          duration: 2500,
        })
      },
      tableRowClassName({row, rowIndex}) {
        if (rowIndex === 1) {
          return 'warning-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return '';
      },

      getExpermentList() {
        getAll().then(data => {
          this.tableData = data;
          this.showDateHandler();
          this.$notify({
            title: '刷新完成',
            type: 'success',
            duration: 2500,
            //data: msg,
          })
        })
      },
      getExperment() {

        this.dialogFormVisible = false;
        edit(this.formDialog).then(data => {
          this.$notify({
            title: '保存完成',
            type: 'success',
            duration: 2500,
            //data: msg,
          });
          this.getExpermentList();
        }).catch(err => {
          this.$notify({
            title: '保存失败',
            type: 'success',
            duration: 2500,
            //data: msg,
          })
        })
      },
      toQuery(){
        if(this.searchKey==''){
          this.getExpermentList();
        }else {
          this.showData= this.showData.filter(_=>
             _.expName.search(this.searchKey.trim())!=-1 || _.expId.search(this.searchKey.trim())!=-1
          )
          console.log(this.showData)
        }

      },
      download(){
        this.$notify({
          title: '等待开发',
          type: 'success',
          duration: 2500,
          //data: msg,
        })
      }
    },

    created() {
      this.getExpermentList()
    }


  }
</script>

<style scoped>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
