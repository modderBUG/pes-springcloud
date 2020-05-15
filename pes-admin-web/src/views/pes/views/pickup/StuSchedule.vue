<template>
  <div>
    <el-divider content-position="left">已选课程</el-divider>

    <div style="width: 80%;min-width: 800px;">

      <TimeSchedule :lessons="lessons" :courses="courses"></TimeSchedule>

    </div>

  </div>
</template>

<script>
  import TimeSchedule from './TimeSchedule'
  import {getStuSchedule} from '../../api/pickup'

  export default {
    name: "StuSchedule",
    components: {TimeSchedule},
    data() {
      return {
        lessons: [
          '早晨',
          '中午',
          '晚上',

        ],
        courses: [
          ['', '', ''],
          ['asdasdas', '', ''],
          ['', '', ''],
          ['', '', ''],
          ['asdasda', '', ''],
          ['', '', ''],
          ['asdadassad', '', ''],
        ],
        showData: [],


      }
    },
    methods: {
      lodingData() {
        getStuSchedule().then(res => {
          /**
           * 转换列表为json
           * */
          let showdata = [];
          let courseList =new Set();
          let courses_= [
            ['', '', ''],
            ['', '', ''],
            ['', '', ''],
            ['', '', ''],
            ['', '', ''],
            ['', '', ''],
            ['', '', ''],
          ]

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
            courseList.add(res[i][0]);
          }



          courseList.forEach(expName=>{
            let group_data= showdata.filter(_=>_.expName==expName)

            let course_chips=[]

            let timeTime=new Set()
            let timeWeek=new Set()
            let timeSchedule=new Set()
            group_data.forEach(item=>{
              timeTime.add(item.timeTimes)
              timeWeek.add(item.timeWeek)
              timeSchedule.add(item.timeSchedule)


            })
            let scheduleTime = '';
            timeSchedule.forEach(_ => {
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

            let expTime_= expName+ '\n第'+[...timeTime] + '周；\n每周' + [...timeWeek] + ';' + scheduleTime + ';';

            timeWeek=[...timeWeek]
            timeSchedule=[...timeSchedule]

            console.log(expTime_)

            for (let i = 0; i < timeWeek.length; i++) {
              for (let j = 0; j < timeSchedule.length; j++) {

                //console.log(parseInt(timeWeek[i]))
               // console.log(parseInt(timeSchedule[j]))

                courses_[parseInt(timeWeek[i]) ][ parseInt(timeSchedule[j])-1]+=expTime_;

              }
            }




          })

          //console.log(this.courses)
          this.courses=courses_
        })
      }

    },
    created() {
      this.lodingData()
    }

  }
</script>

<style scoped>

</style>
