<template>
  <div class="app-container">


    <el-divider content-position="left">开学时间相关设置</el-divider>



    <!--卡片 资料中心-->
    <el-card class="box-card" style="width: 500px">
      <div slot="header" class="clearfix">
        <span>设置表单</span>

      </div>
      <div class="margin1">
        <el-form :label-position="labelPosition" label-width="80px" :model="termForm" :rules="rules">
          <el-form-item label="开学时间" :label-width="formLabelWidth" prop="termStart">
            <el-date-picker
              v-model="termForm.termStart"
              type="date"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="学期" :label-width="formLabelWidth" prop="expTerm">
            <el-select v-model="termForm.expTerm" >
              <el-option label="第一学期" :value=1></el-option>
              <el-option label="第二学期" :value=2></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="实验周数" :label-width="formLabelWidth" prop="termWeeks" >
            <el-input v-model="termForm.termWeeks" ></el-input>
          </el-form-item>
          <el-form-item label="周选限制" :label-width="formLabelWidth" prop="weekLimit">
            <el-input v-model="termForm.weekLimit" ></el-input>
          </el-form-item>
          <el-form-item label="学期选限制" :label-width="formLabelWidth" prop="termLimit">
            <el-input v-model="termForm.termLimit" ></el-input>
          </el-form-item>
          <el-form-item label="退选限制" :label-width="formLabelWidth" prop="returnLimit">
            <el-input v-model="termForm.returnLimit" ></el-input>
          </el-form-item>
          <el-row>
            <el-button type="primary" icon="el-icon-refresh" @click="getTermInfo">读取最新数据</el-button>
            <el-button type="danger" icon="el-icon-edit" @click="onSubmit">确认修改</el-button>

          </el-row>

        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>

  import {getTermInfo, edit} from '@/api/initsetting'
  import {pickUpCourse,getSchedule,returnCourse} from '../../api/AdvanceQuery'

  export default {
    name: 'initsetting',

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
        labelPosition: 'right',
        formLabelWidth:'100px',
        testingverb:'A000',
        termForm: {
          id:'',
          termStart: '',
          expTerm: '',
          termWeeks: '',
          weekLimit: '',
          termLimit: '',
          returnLimit: '',

        },
        rules: {
          termStart: [
            { validator: checkNull, trigger: 'blur' }
          ],
          expTerm: [
            { validator: checkNull, trigger: 'blur' }
          ],
          termWeeks: [
            { validator: checkNull, trigger: 'blur' }
          ],
          weekLimit: [
            { validator: checkNull, trigger: 'blur' }
          ],
          termLimit: [
            { validator: checkNull, trigger: 'blur' }
          ],
          returnLimit: [
            { validator: checkNull, trigger: 'blur' }
          ],
        },
      }
    },
    created() {
      this.getTermInfo()
    },
    methods: {
      onSubmit() {
        this.setTermInfo();
      }

      ,
      formatDate(row, column) {
        let date = new Date(row);
        let Y = date.getFullYear() + '-';
        let M = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) + '-' : date.getMonth() + 1 + '-';
        let D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
        let h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
        let m = date.getMinutes()  < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':';
        let s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
        return Y + M + D + h + m + s;
      },
      setTermInfo() {

        this.termForm.termStart=this.formatDate(this.termForm.termStart).split(" ")[0]
        edit(this.termForm).then(msg => {
          this.$notify({
            title: '修改成功',
            type: 'success',
            duration: 2500,
            //data: msg,
          })
        })
      },
      getTermInfo() {
        getTermInfo().then(data => {this.termForm = data[0];
          this.$notify({
            title: '刷新完成',
            type: 'success',
            duration: 2500,
            //data: msg,
          })})

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
