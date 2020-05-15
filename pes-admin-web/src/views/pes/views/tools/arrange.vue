<template>
  <div style="padding:20px 20px 20px 20px;">
    <el-card class="box-card" style="width: 80%;min-width: 400px;padding:20px 20px 20px 20px;" body-style>

      <el-form :inline="true" :model="form" class="demo-form-inline">
        <el-form-item label="排课周">
          <el-input v-model="form.week" placeholder="1-6"></el-input>
        </el-form-item>
        <el-form-item label="实验">
          <el-select v-model="form.expList" placeholder="请选择实验">
            <!--            遍历课程列表-->
            <el-option v-for="i in tableData" :label="i.expName" :value="i.expId"></el-option>

          </el-select>
        </el-form-item>

        <!--        <el-form-item>-->
        <!--          <el-button type="primary" @click="onSubmit">查询</el-button>-->
        <!--        </el-form-item>-->


        <el-divider></el-divider>

        <!--      排课情况-->
        <div>
          <table class="gridtable">
            <tr>
              <td></td>
              <td>周一</td>
              <td>周二</td>
              <td>周三</td>
              <td>周四</td>
              <td>周五</td>
              <td>周六</td>
              <td>周日</td>
            </tr>
            <tr>
              <td>上午</td>
              <td v-for="i in 7">
                <template>
                  <el-select v-model="value1[i]" placeholder="请选择">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.teacherName"
                      :value="item.teacherId">
                    </el-option>
                  </el-select>
                </template>
              </td>
            </tr>

            <tr>
              <td>下午</td>
              <td v-for="i in 7">
                <template>
                  <el-select v-model="value2[i]" placeholder="请选择">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.teacherName"
                      :value="item.teacherId">
                    </el-option>
                  </el-select>
                </template>
              </td>
            </tr>
            <tr>
              <td>晚上</td>
              <td v-for="i in 7">
                <template>
                  <el-select v-model="value3[i]" placeholder="请选择" class="innerred">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.teacherName"
                      :value="item.teacherId">
                    </el-option>
                  </el-select>
                </template>

              </td>
            </tr>
          </table>
        </div>
        <el-form-item style="padding-bottom: 5px; padding-top: 5px;">
          <el-button type="primary" @click="onSubmit" icon="el-icon-finished">确定</el-button>
          <el-button type="primary" @click="cleanForm" icon="el-icon-refresh-right">刷新</el-button>
        </el-form-item>
      </el-form>


      <!--      <template>-->
      <!--        <el-select v-model="value" placeholder="请选择">-->
      <!--          <el-option-->
      <!--            v-for="item in options"-->
      <!--            :key="item.value"-->
      <!--            :label="item.label"-->
      <!--            :value="item.value">-->
      <!--          </el-option>-->
      <!--        </el-select>-->
      <!--      </template>-->

    </el-card>

    <el-divider content-position="left">排课结果预览</el-divider>
    <ScheduleView v-if="hackReset == true"></ScheduleView>
  </div>
</template>

<script>
  import {getAll} from "../../api/ExpermentTable";
  import {getAllTeachers} from "../../api/TeacherQury";
  import {setTimeList, delExprimentTime} from "../../api/TimeTable";
  import ScheduleView from "../components/ScheduleView"

  export default {
    components: {
      ScheduleView
    },
    name: "arrange",
    data() {
      return {
        hackReset: true,
        form: {
          week: '',
          expList: '',
        },
        tableData: [],
        formOptions: [],

        options: [{
          value: '002',
          label: '于春娜',
          teacherName: '',
          teacherId: '',
        }, {
          value: '003',
          label: '金恩姬'
        }, {
          value: '004',
          label: '于春娜'
        }, {
          value: '005',
          label: '于春娜'
        }, {
          value: '006',
          label: '金恩姬'
        }],
        value1: [],
        value2: [],
        value3: [],

      }
    },
    methods: {
      onSubmit() {
        //console.log('submit!');
        var start = parseInt(this.form.week.split("-")[0]) ;
        var end = parseInt(this.form.week.split("-")[1]);
        var target = this.form.expList;
        if (end == '' || start > end || target=='') {
          this.$notify({
            title: '输入的周数不合法',
            type: 'success',
            duration: 2500,
            //data: msg,
          })
          return;
        }
        this.hackReset = false;       //准备刷新子组件
        var TimeForm = [];
        for (var i = start; i <= end; i++) {
          for (var j = 1; j <= 7; j++) {
            if (this.value1[j] != '' && this.value1[j] != null && this.value1[j] != undefined) {
              var item = {
                expId: target,
                expTime: '' + i + j + 1 + '',
                timeTimes: i,
                timeWeek: j,
                timeSchedule: 1,
                teacherId: this.value1[j]
              };
              TimeForm.push(item)
            }
            if (this.value2[j] != '' && this.value2[j] != null && this.value2[j] != undefined) {
              var item = {
                expId: target,
                expTime: '' + i + j + 2 + '',
                timeTimes: i,
                timeWeek: j,
                timeSchedule: 2,
                teacherId: this.value2[j]
              };
              TimeForm.push(item)
            }
            if (this.value3[j] != '' && this.value3[j] != null && this.value3[j] != undefined) {
              var item = {
                expId: target,
                expTime: '' + i + j + 3 + '',
                timeTimes: i,
                timeWeek: j,
                timeSchedule: 3,
                teacherId: this.value3[j]
              };
              TimeForm.push(item)
            }
          }
        }
        console.log(TimeForm)
        setTimeList(TimeForm).then(_ => {
            this.$notify({
              title: '设置成功',
              type: 'success',
              duration: 2500,
              //data: msg,
            })
          //一些刷新操作
            this.getData()
            this.hackReset = true
            this.cleanForm()
          }
        ).catch(_ => {
          this.$notify({
            title: '设置失败',
            type: 'warn',
            duration: 2500,
            //data: msg,
          })
          this.hackReset = true
        })


      },
      cleanForm() {
        this.value1 = [];
        this.value2 = [];
        this.value3 = [];
      },
      getData() {
        getAll().then(data => {
          this.tableData = data;

        })
        getAllTeachers().then(data => {
          this.options = data;
        })
      },
    },
    created() {
      //获取教师列表
      //获取实验列表
      this.getData()


    }
  }

</script>

<style scoped>
  .innerred {

  }

  table.gridtable {
    font-family: verdana, arial, sans-serif;
    font-size: 20px;
    color: #333333;
    border-width: 1px;
    border-color: #666666;
    border-collapse: collapse;
  }

  table.gridtable th {
    border-width: 1px;
    padding: 8px;
    border-style: solid;
    border-color: #666666;
    background-color: #dedede;
  }

  table.gridtable td {
    border-width: 1px;
    padding: 8px;
    border-style: solid;
    border-color: #666666;
    background-color: #ffffff;
  }

</style>
