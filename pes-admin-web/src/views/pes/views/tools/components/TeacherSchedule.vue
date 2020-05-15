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
            label="实验周次"
            width="260">
          </el-table-column>

<!--          <el-table-column-->
<!--            fixed="right"-->
<!--            label="操作"-->
<!--          >-->
<!--            <template slot-scope="scope">-->
<!--              <el-button @click="handleClick(scope.row)" type="text" size="small"><i class="el-icon-check"></i>选课-->
<!--              </el-button>-->
<!--              &lt;!&ndash;              <el-button type="text" size="small">编辑</el-button>&ndash;&gt;-->
<!--            </template>-->
<!--          </el-table-column>-->
        </el-table>
        <div v-else style="text-align: center;">您还没有选择课程</div>
      </template>
    </div>


  </div>
</template>

<script>
  import {get} from "../../../api/Freetime";

  export default {
    name: "TeacherSchedule",
    data() {
      var checkNull = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('数据不能为空'));
        }

      };
      return {
        dialogVisible: false,
        flag: true,
        showData: [],
        value: '',
        formInline: {
          user: '',
          region: ''
        },


      }
    },

    mounted() {
      this.getData()
    },
    methods: {

      getData() {
        get("?teacherName=true").then(res => {
          console.log(res)
          // this.tableData=res
          if (res.length == 0) this.flag = false;
          for (let i = 0; i < res.length; i++) {
            var item = {}
            item = res[i]

            var time = res[i].timeTimes.split("-")[0] + "-" + res[i].timeTimes.split("-")[res[i].timeTimes.split("-").length - 1] + "周；"
            var week = ""
            for (let j = 0; j < res[i].timeWeek.split("-").length; j++) {
              console.log(res[i].timeWeek.split("-"))
              let time123 = ''
              if (res[i].timeSchedule.split("-")[j] == '1') time123 = "，早晨"
              if (res[i].timeSchedule.split("-")[j] == '2') time123 = "，下午"
              if (res[i].timeSchedule.split("-")[j] == '3') time123 = "，晚上"

              week += "星期" + res[i].timeWeek.split("-")[j] + time123

            }
            item.time = time + week

            this.showData.push(item)
          }
        })
      },
    }
  }
</script>

<style scoped>

</style>
