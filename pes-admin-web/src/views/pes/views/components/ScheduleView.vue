<template>
  <div style="padding: 20px 20px 20px 20px">
    <el-card class="box-card" style="width: 80%;min-width: 400px;padding:20px 20px 20px 20px;" body-style>
      <div slot="header" class="clearfix">
        <el-radio-group v-model="radio3" @change="handlerSelect(radio3)">
          <el-radio-button v-for="(item,index) in this.termForm.termWeeks" :label="item">第{{item}}周</el-radio-button>
        </el-radio-group>
      </div>
      <el-table
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column
          label="实验名称"
          prop="expName">
        </el-table-column>
        <el-table-column
          label="星期"
          sortable
          prop="timeWeek">
        </el-table-column>
        <el-table-column
          label="时段"
          sortable
          prop="timeSchedule">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small">移除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

  </div>
</template>

<script>
  import {getTermInfo, edit} from '@/api/initsetting'
  import {
    findExperimentByTimeTimes,
  } from "../../api/AdvanceQuery";
  import {delExprimentTime} from "../../api/TimeTable";

  export default {
    props: [],
    name: "ScheduleView",
    data() {
      return {
        radio3: 1,
        radio4: '上海',
        radio5: '上海',
        weeks: '',
        termForm: '',
        tableData: [],
      };
    },
    methods: {
      handleClick(index){
        delExprimentTime(index.expId).then(res=>{
          this.$notify({
            title: '删除成功',
            type: 'success',
            duration: 2500,
            data: index.expName,
          })
          this.getTermInfo();
          this.getExperimentByWeek(1)
        })
        console.log(index.expId)

      },
      getTermInfo() {
        getTermInfo().then(data => {
          this.termForm = data[0];
        })

      },
      handlerSelect(index) {
        this.getExperimentByWeek(index)

        //console.log("index============="+index);
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
          this.tableData=list;


        })
      },
    },
    created() {
      this.getTermInfo();
      this.getExperimentByWeek(1)
    }
  }
</script>

<style scoped>

</style>
