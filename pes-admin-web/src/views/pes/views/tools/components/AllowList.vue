<template>
  <div style="margin: 20px 20px 20px 20px;padding: 20px 20px 20px 20px ">

    <!--选课页面-->
    <el-divider content-position="left">已选课程</el-divider>
    <div class="my-container">
      <template>
        <el-table v-if="flag"
                  :data="showData"
                  stripe
                  style="width: 100%">
          <el-table-column
            prop="expName"
            label="实验名称"
            width="260">
          </el-table-column>
          <el-table-column
            prop="time"
            label="实验时间"
            width="500">
          </el-table-column>
          <el-table-column
            prop="teacherId"
            width="260"
            label="任课老师">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row)" type="text" size="small">删除
              </el-button>
              <!--              <el-button type="text" size="small">编辑</el-button>-->
            </template>
          </el-table-column>
        </el-table>
        <div v-else style="text-align: center;">您还没有排课</div>
      </template>
    </div>
  </div>
</template>

<script>
  import {get,delOne} from "../../../api/Freetime";
    export default {
        name: "AllowList",

      data(){
          return{
            flag: true,
            tableData: [{
              id:'',
              expId:'',
              expName:'',
              timeTimes:'',
              timeWeek:'',
              timeSchedule:'',
              teacherId:''
            }],
            showData:[]
          }
      },
      methods:{

        handleClick(item){
          console.log(item.id)
          delOne(item.id).then(res=>{
            console.log(res)
          })
        },

          getData(){
            get("?manager=true").then(res=>{
              // this.tableData=res
              if (res.length==0)  this.flag = false;
              for (let i = 0; i <res.length ; i++) {
                var item={}
                item=res[i]

                var time=res[i].timeTimes.split("-")[0]+"-"+res[i].timeTimes.split("-")[res[i].timeTimes.split("-").length-1]+"周；"
                var week=""
                for (let j = 0; j <res[i].timeWeek.split("-").length ; j++) {
                  console.log(res[i].timeWeek.split("-"))
                  let time123=''
                  if (res[i].timeSchedule.split("-")[j]=='1')  time123="，早晨"
                  if (res[i].timeSchedule.split("-")[j]=='2')  time123="，下午"
                  if (res[i].timeSchedule.split("-")[j]=='3')  time123="，晚上"

                  week+="星期"+res[i].timeWeek.split("-")[j]+time123

                }
                item.time=time+week

                this.showData.push(item)
              }
            })
          }

      },
      mounted() {
          this.getData()
      }
    }
</script>

<style scoped>
  .my-container {
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    width: 80%;
    min-width: 500px;
    min-height: 60px;
    padding: 40px 40px 40px 40px;
  }

</style>
