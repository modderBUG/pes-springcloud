<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="16"><div class="grid-content bg-purple">

        <el-tabs type="border-card" style="overflow: hidden">
          <el-tab-pane label="不确定度计算器">
            <cal01></cal01></el-tab-pane>
          <el-tab-pane label="转动惯量计算器">
            <cal02></cal02></el-tab-pane>
          <el-tab-pane label="密立根油滴实验数据处理系统">
            <div>
              <h3> 该工具无法在线预览 您还可以：</h3>
              <p> 下载工具：</p>
              <p><i class="el-icon-document-copy">&nbsp;</i><a href=""> 密立根油滴实验数据处理系统1.6 </a><i class="el-icon-download" style="float: right"></i></p>

            </div>
            </el-tab-pane>
          <el-tab-pane label="光电效应测普朗克计算器">
            <h3> 该工具无法在线预览 您还可以：</h3>
            <p> 下载工具：</p>
            <p><i class="el-icon-document-copy">&nbsp;</i><a href=""> 光电效应测普朗克计算器 </a><i class="el-icon-download" style="float: right"></i></p>
          </el-tab-pane>
        </el-tabs>

      </div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
    </el-row>
  </div>
</template>

<script>
  import cal01 from './tools/cal01.vue'
  import cal02 from './tools/cal02.vue'
  export default {
    components:{cal01,cal02},
    name: "firstpage",
    data() {
      return {

        value: new Date(),
        reverse: true,
        activities: [{
          content: '活动按期开始',
          timestamp: '2018-04-15'
        }, {
          content: '通过审核',
          timestamp: '2018-04-13'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }],
      }
    },
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
