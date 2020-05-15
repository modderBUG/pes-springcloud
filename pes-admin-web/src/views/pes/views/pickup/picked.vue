<template>
  <div style="margin: 20px 20px 20px 20px;padding: 20px 20px 20px 20px ">

    <!--选课页面-->
    <el-divider content-position="left">已选课程</el-divider>

    <div class="my-container">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="关键字">
          <el-input v-model="formInline.user" placeholder="关键字"></el-input>
        </el-form-item>
        <el-form-item label="实验">
          <el-select v-model="value" placeholder="请选择" @change="toSelect(value)">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="toQuery"><svg-icon icon-class="search" />查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="my-container">
      <template>
        <el-table v-if="flag"
                  :data="showeData"
                  stripe
                  style="width: 100%">
          <el-table-column
            prop="expName"
            label="实验名称"
            width="260">
          </el-table-column>
          <el-table-column
            prop="expTime"
            label="实验周次"
            width="260">
          </el-table-column>
          <el-table-column
            prop="teacherName"
            width="260"
            label="任课老师">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row)" type="text" size="small">退课
              </el-button>
              <!--              <el-button type="text" size="small">编辑</el-button>-->
            </template>
          </el-table-column>
        </el-table>
        <div v-else style="text-align: center;">您还没有选择课程</div>
      </template>
    </div>


  </div>
</template>

<script>
  import {returnCourse, getStuSchedule} from '../../api/pickup'

  export default {
    name: 'picked',

    data() {
      var checkNull = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('数据不能为空'));
        }

      };
      return {
        dialogVisible: false,
        showeData2: '',
        flag: true,
        showeData: [{
          expName: 'aaaaa',
          expTime: '1-14周；周二、周五；每晚上9点',
          teacherName: 'accccc'
        }, {
          expName: 'aaaaa',
          expTime: '1-14周；周二、周五；每晚上9点',
          teacherName: 'accccc'
        }, {
          expName: 'aaaaa',
          expTime: '1-14周；周二、周五；每晚上9点',
          teacherName: 'accccc'
        }, {
          expName: 'aaaaa',
          expTime: '1-14周；周二、周五；每晚上9点',
          teacherId: 'accccc'
        }, {
          expName: 'aaaaa',
          expTime: 'bbbbbb',
          teacherId: 'accccc'
        },],
        options: [],
        value: '',
        formInline: {
          user: '',
          region: '',
          searchKey:''
        },


      }
    },
    created() {
      this.lodingTableData()


    },
    methods: {
      toQuery(){
        if(this.formInline.searchKey==''){
          this.lodingTableData()
        }else {
          this.showData= this.showData.filter(_=>
            _.expName.search(this.formInline.searchKey.trim())!=-1 || _.expTime.search(this.formInline.searchKey.trim())!=-1
          )
        }

      },
      toSelect(key){

        this.showData= this.showData.filter(_=>
          _.expName.search(key.trim())!=-1
        )
        console.log(key)
      },

      lodingTableData() {
        getStuSchedule()
          .then(res => {

            if (res == '' || res == undefined) {
              this.flag = false;
              return
            }

            console.log(res.length - 1)

            var courseList = [];
            var showdata = [];
            var dealdata = [];


            /**
             * 转换列表为json
             * */
            for (var i = 0; i < res.length; i++) {
              var obj = {
                expName: res[i][0],
                expId: res[i][1],
                expTime: res[i][2],
                timeTimes: res[i][3],
                timeWeek: res[i][4],
                timeSchedule: res[i][5],
                teacherName: res[i][6],
              };
              showdata.push(obj);
              courseList.push(res[i][0]);
            }


            /**
             * 筛选数据放入选择框
             * */
            var courseSet = [...new Set(courseList)]; //去重


            var dealList = [];
            for (var j = 1; j <= courseSet.length; j++) {

              var option = {
                value: j,
                label: courseSet[j-1]
              };
              dealList.push(option)
            }
            this.options = dealList;


            /**
             * 筛选数据放入选课表
             * */
            courseSet.forEach(_ => {
              var e = showdata.filter(exp => exp.expName == _);
              e = e.sort(function (a, b) {
                return a.timeTimes - b.timeTimes;
              });
              var time =e[0].timeTimes + '-' + e[e.length - 1].timeTimes + '周;'
              var week = [];
              var schedule = [];
              e.forEach(_ => {
                week.push(_.timeWeek);
                schedule.push(_.timeSchedule)
              });
              week = [...new Set(week)];
              schedule = [...new Set(schedule)];
              var scheduleTime = '';
              schedule.forEach(_ => {
                if (_ == 1) {
                  scheduleTime += ' 早晨 '
                }
                if (_ == 2) {
                  scheduleTime += '下午 '
                }
                if (_ == 3) {
                  scheduleTime += ' 晚上 '
                }
              })

              var obj = {
                expName: e[0].expName,
                expId: e[0].expId,
                expTime: time + '每周' + week + ';' + scheduleTime + ';',
                teacherName: e[0].teacherName,
              }

              dealdata.push(obj)

            })

            this.showeData = dealdata;


          }).catch(e => {
          this.$message({
            showClose: true,
            message: e,
            type: 'error'
          });
        })


      },

      handleClick(item) {
        this.$confirm('退选本课程？')
          .then(_ => {
            const h = this.$createElement;
            this.$message({
              message: h('p', null, [
                h('span', null, '退选本课程 '),
                h('i', {style: 'color: teal'}, '请稍后')
              ])
            });

            let args='expId='+item.expId+'&teacherName='+item.teacherName
            returnCourse(args).then(res => {
              this.$message({
                showClose: true,
                message: res.msg == undefined ? res.err : res.msg,
                type: res.msg != undefined ? 'success' : 'error'
              });
              this.lodingTableData()
            }).catch(e => {
              this.$message({
                showClose: true,
                message: '失败！' + e.toString(),
                type: 'error'
              });
            })

            done();
          })
          .catch(_ => {
            console.log('取消了操作')
          });

      },
      onSubmit() {

      },
      testing() {


      },
      testing01() {

      }
      ,
      testing02() {

      }
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
