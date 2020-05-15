<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="16"><div class="grid-content bg-purple">

        <div class="block">
          <el-timeline>
            <el-timeline-item v-for="item in tableData" :timestamp="item.updateTime" placement="top">
              <el-card>
                <h4>{{item.editorContent}}</h4>
                <p>{{item.editorAuthor}}</p>
              </el-card>
            </el-timeline-item>

          </el-timeline>
        </div>

      </div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
    </el-row>
  </div>
</template>

<script>
  import cal01 from './tools/cal01.vue'
  import {parseTime} from "../../../../utils/index";
  import {getAll} from '../../api/PushMessage'
  export default {
    components:{cal01},
    name: "firstpage",
    data() {
      return {

        value: new Date(),
        reverse: true,
        activities: [{
          content: '活动按期开始',
          timestamp: '2018-04-15',
          author:'王小虎 提交于 2018/4/12 20:46'
        }, {
          content: '通过审核',
          timestamp: '2018-04-13',
          author:'王小虎 提交于 2018/4/12 20:46'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11',
          author:'王小虎 提交于 2018/4/12 20:46'
        }],
        tableData:[],
      }
    },
    methods:{
      parseTime,
      getInitData(){
        let args='?limit=5';
        getAll(args).then(res=>{
          this.tableData=res
          this.tableData.forEach(_=>{
            _.updateTime=parseTime(_.updateTime)
          })
        })
      }

    },
    mounted() {
      this.getInitData()
    }
  }
</script>

<style scoped>


  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 100%;
  }

  .el-row {
    margin-bottom: 20px;
  }

  .last-child {
    margin-bottom: 0;
  }

  .el-col {
    border-radius: 4px;
  }

  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

  .el-col {
    border-radius: 4px;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 160px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }

  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
