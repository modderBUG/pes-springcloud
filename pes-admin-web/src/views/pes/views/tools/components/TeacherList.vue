<template>
  <div style="margin: 20px 20px 20px 20px;padding: 20px 20px 20px 20px ">

    <!--选课页面-->
    <el-divider content-position="left">教师抢课</el-divider>

    <div class="my-container">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="关键字">
          <el-input v-model="formInline.searchKey" placeholder="关键字"></el-input>
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
          <el-button type="primary" @click="toQuery" ><svg-icon icon-class="search" />查询</el-button>
        </el-form-item>
      </el-form>
    </div>
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

          <el-table-column
            fixed="right"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row)" type="text" size="small"><i class="el-icon-check"></i>选课
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
  import {get,pickUp} from "../../../api/Freetime";

  export default {
    name: 'TeacherList',

    data() {
      var checkNull = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('数据不能为空'));
        }

      };
      return {
        dialogVisible: false,
        flag: true,
        showData:[],
        options: [],
        value: '',
        formInline: {
          user: '',
          region: '',
          searchKey:'',
        },


      }
    },
    created() {



    },

    mounted(){
      this.getData()
    },
    methods: {

      toQuery(){
        if(this.formInline.searchKey==''){
          this.getData()
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

      getData(){
        get("?teacher=true").then(res=>{
          console.log(res)
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
      },


      handleClick(item) {
        this.$confirm('确认选择本课程？')
          .then(_ => {
            const h = this.$createElement;
            this.$message({
              message: h('p', null, [
                h('span', null, '正在尝试选课 '),
                h('i', {style: 'color: teal'}, '请稍后')
              ])
            });

            pickUp(item.id).then(res => {
              this.$message({
                showClose: true,
                message: res.msg == undefined ? res.err : res.msg,
                type: res.msg != undefined ? 'success' : 'error'
              });
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
      onSubmit(){}
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
