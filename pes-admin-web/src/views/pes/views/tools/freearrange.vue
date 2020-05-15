<template>
  <div style="padding:20px 20px 20px 20px;">

    <el-card class="box-card" style="width: 50%;">
      <div style="padding:20px 20px 20px 20px;">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="周次">
            <el-input v-model="timeTime" placeholder="1-16"></el-input>
          </el-form-item>
        <el-form-item label="实验">
          <el-select v-model="expID" placeholder="请选择实验">
            <!--            遍历课程列表-->
            <el-option v-for="i in tableData" :label="i.expName" :value="i.expId"></el-option>

          </el-select>
        </el-form-item>
        </el-form>
        <div style="width: 600px;padding:0px 50px 20px 20px;">
          <el-checkbox-group v-model="checkList" >
            <div class="groupa">
              <el-checkbox label="1" style="">
                周一
              </el-checkbox>
              <el-checkbox-group v-model="week1">
                <el-checkbox-button label="1">上午</el-checkbox-button>
                <el-checkbox-button label="2">中午</el-checkbox-button>
                <el-checkbox-button label="3">下午</el-checkbox-button>
              </el-checkbox-group>
            </div>

            <div class="groupa">
              <el-checkbox label="2">
                周二
              </el-checkbox>
              <el-checkbox-group v-model="week2">
                <el-checkbox-button label="1">上午</el-checkbox-button>
                <el-checkbox-button label="2">中午</el-checkbox-button>
                <el-checkbox-button label="3">下午</el-checkbox-button>
              </el-checkbox-group>
            </div>

            <div class="groupa">
              <el-checkbox label="3">
                周三
              </el-checkbox>
              <el-checkbox-group v-model="week3">
                <el-checkbox-button label="1">上午</el-checkbox-button>
                <el-checkbox-button label="2">中午</el-checkbox-button>
                <el-checkbox-button label="3">下午</el-checkbox-button>
              </el-checkbox-group>
            </div>

            <div class="groupa">
              <el-checkbox label="4">
                周四
              </el-checkbox>
              <el-checkbox-group v-model="week4">
                <el-checkbox-button label="1">上午</el-checkbox-button>
                <el-checkbox-button label="2">中午</el-checkbox-button>
                <el-checkbox-button label="3">下午</el-checkbox-button>
              </el-checkbox-group>
            </div>

            <div class="groupa">
              <el-checkbox label="5">
                周五
              </el-checkbox>
              <el-checkbox-group v-model="week5">
                <el-checkbox-button label="1">上午</el-checkbox-button>
                <el-checkbox-button label="2">中午</el-checkbox-button>
                <el-checkbox-button label="3">下午</el-checkbox-button>
              </el-checkbox-group>
            </div>

          </el-checkbox-group>
          <el-row>
            <el-button type="primary" @click="submitItem()" icon="el-icon-finished">排课</el-button>
            <el-button type="success" icon="el-icon-refresh-right">刷新</el-button>
          </el-row>
        </div>
      </div>
    </el-card>
<AllowList></AllowList>
  </div>
</template>

<script>

  import {getAll} from "../../api/ExpermentTable";
  import {delOne,addOne} from "../../api/Freetime";
  import AllowList from "./components/AllowList"
  export default {
    name: "freearrange",
    components:{AllowList},
    data() {
      return {
        checkList: ['4', '1'],
        tableData: [],
        expID:'',
        timeTime: '',
        week1: [],
        week2: [],
        week3: [],
        week4: [],
        week5: [],
      };
    },
    methods: {
      submitItem(){
        if (this.checkList.length==0 || expId=='' ||this.timeTime.split("-").length!=2){
          this.$message.error('参数输入不当！');
          return
        }
        var expId=this.expID
        var timeTimes=this.timeTime.split("-")[0]
        var timeWeek=this.checkList[0]
        var timeSchedule=""

        const start=parseInt(this.timeTime.split("-")[0])
        const end=parseInt(this.timeTime.split("-")[1])
        for (let i = start+1; i <=end ; i++) {
          timeTimes+="-"+i;
        }

     for1: for (let i = 0; i <this.checkList.length ; i++) {
          if (i!=0) timeWeek+="-"+this.checkList[i]
          console.log(this.checkList[i])
          switch1:   switch (this.checkList[i]) {
            case '1':timeSchedule+="-"+this.week1[0];break switch1;
            case '2':timeSchedule+="-"+this.week2[0];break switch1;
            case '3':timeSchedule+="-"+this.week3[0];break switch1;
            case '4':timeSchedule+="-"+this.week4[0];break switch1;
            case '5':timeSchedule+="-"+this.week5[0];break switch1;
            default:console.log("error:"+this.checkList[i])
          }
        }
        timeSchedule=timeSchedule.substring(1)
        var Freetime={
          expId:expId,
          timeTimes:timeTimes,
          timeWeek:timeWeek,
          timeSchedule:timeSchedule,
        }
        console.log(Freetime)
        addOne(Freetime).then(res=>{
          console.log(res)
        })
      },



    },
    mounted() {
      getAll().then(data => {
        this.tableData = data;

      })
    }
  };
</script>

<style scoped>
  .groupa {
    padding-bottom: 5px;
    padding-top: 5px;
    padding-right: 50px;
    display: inline-list-item;

  }
</style>
