<template>
  <div>

    <el-form ref="form" :model="form" label-width="65px">
      <h3>大学物理实验：扭摆法测转动惯量实验数据处理 </h3>



      <div>


        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>匀减速：</span>
            <!--        <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
          </div>
          <div class="mytable">
            <table style="text-align: center;overflow: auto;">
              <tr>
                <td><a style="color: #f41776">K：</a></td><td>1</td>
                <td><a style="color: #f41776">2</a></td><td>3</td>
                <td><a style="color: #f41776">4</a></td><td>5</td>
                <td><a style="color: #f41776">6</a></td><td>7</td>
                <td><a style="color: #f41776">8</a></td><td>平均</td>
              </tr>
              <tr>
                <td><a style="color: #f41776">t(s)：</a></td><td><el-input  type="text" v-model="result[0]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[1]"></el-input></a></td><td><el-input  v-model="result[2]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[3]"></el-input></a></td><td><el-input  v-model="result[4]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[5]"></el-input></a></td><td><el-input  v-model="result[6]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[7]"></el-input></a></td><td></td>
              </tr>
              <tr>
                <td><a style="color: #f41776">m砝码（千克）：</a></td><td><el-input type="text" v-model="result[8]"></el-input></td>
              </tr>
              <tr>
                <td><a style="color: #f41776">β</a></td><td><el-input  type="text" v-model="result[0]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[1]"></el-input></a></td><td><el-input  v-model="result[2]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[3]"></el-input></a></td><td><el-input  v-model="result[4]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[5]"></el-input></a></td><td><el-input  v-model="result[6]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[7]"></el-input></a></td><td></td>
              </tr>
            </table>
          </div>
        </el-card>

        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>匀加速：</span>
            <!--        <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
          </div>
          <div class="mytable">
            <table style="text-align: center;overflow: auto;">
              <tr>
                <td><a style="color: #f41776">K：</a></td><td>1</td>
                <td><a style="color: #f41776">2</a></td><td>3</td>
                <td><a style="color: #f41776">4</a></td><td>5</td>
                <td><a style="color: #f41776">6</a></td><td>7</td>
                <td><a style="color: #f41776">8</a></td><td>平均</td>
              </tr>
              <tr>
                <td><a style="color: #f41776">t(s)：</a></td><td><el-input  type="text" v-model="result[0]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[1]"></el-input></a></td><td><el-input  v-model="result[2]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[3]"></el-input></a></td><td><el-input  v-model="result[4]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[5]"></el-input></a></td><td><el-input  v-model="result[6]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[7]"></el-input></a></td><td></td>
              </tr>
              <tr>
                <td><a style="color: #f41776">β</a></td><td><el-input  type="text" v-model="result[0]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[1]"></el-input></a></td><td><el-input  v-model="result[2]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[3]"></el-input></a></td><td><el-input  v-model="result[4]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[5]"></el-input></a></td><td><el-input  v-model="result[6]"></el-input></td>
                <td><a style="color: #f41776"><el-input  v-model="result[7]"></el-input></a></td><td></td>
              </tr>
            </table>
          </div>
        </el-card>

        <el-row style="margin-top: 5px">

          <el-button type="primary">计算β</el-button>
          <el-button type="primary">计算J</el-button>
          <el-button type="danger">清楚数据<i class="el-icon-delete"></i></el-button>

        </el-row>
        <h5>J=</h5>

      </div>


    </el-form>



  </div>
</template>

<script>
  export default {
    name: "firstpage",
    data() {
      return {
        answer:'',
        result:[0,0,0,0,0],
        radio:'1',
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        }
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!');
      }
    }
  }
</script>

<style scoped>
  .mytable{
    overflow: auto;
  }
.el-input{
  max-width: 65px;
  margin: 10px 10px 10px 10px;
}

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
