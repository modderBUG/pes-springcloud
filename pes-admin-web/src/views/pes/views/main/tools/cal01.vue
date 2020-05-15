<template>
  <div>

    <el-form ref="form" :model="form" label-width="65px">
      <h3>大学物理实验：不确定度计算器 </h3>
        <p>
          <el-radio v-model="radio" label="1">直接测量 </el-radio>
          <el-radio v-model="radio" label="2">间接测量</el-radio>
        </p>


      <div v-if="radio=='1'">
        <p> 使用说明：</p>
        <p>  1.输入直接测量的数据个数，并点击“确定”；</p>
        <p>2.在表格中依次输入数据和仪器误差，并点击“计算”。 </p>
        <p>计算公式： </p>
        <p><img src="Uzz.gif"/></p>
        <p><img src="UZ.gif"/></p>
        <table>
          <tr>
            <td>请输入参数个数：</td><td><el-input type="text" v-model="answer"></el-input></td>
          </tr>
          <tr>
            <td></td><td><el-button type="warning">计算</el-button></td>
          </tr>
        </table>

        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>计算结果：</span>
            <!--        <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
          </div>
          <div>
            <table>
              <tr>
                <td><a style="color: #f41776">平均值：</a></td><td><el-input :disabled="true" v-model="result[0]"></el-input></td>
              </tr>
              <tr>
                <td><a style="color: #f41776">A类不确定度：</a></td><td><el-input :disabled="true"  v-model="result[1]"></el-input></td>
              </tr>
              <tr>
                <td><a style="color: #f41776">B类不确定度：</a></td><td><el-input :disabled="true" v-model="result[2]"></el-input></td>
              </tr>
              <tr>
                <td><a style="color: #f41776">直接不确定度：</a></td><td><el-input :disabled="true"  v-model="result[3]"></el-input></td>
              </tr>
              <tr>
                <td><a style="color: #f41776">相对不确定度：</a></td><td><el-input :disabled="true"  v-model="result[4]"></el-input></td>
              </tr>
            </table>
          </div>
        </el-card>
      </div>

      <div  v-if="radio=='2'">
        <p> 使用说明：</p>
        <p>  1.输入直接测量的数据个数，并点击“确定”；</p>
        <p>2.填入直接测量量的不确定度和偏导数以及间接量的计算值，点击“计算”。 </p>
        <p>计算公式： </p>
        <p><img src="N.gif"/></p>
        <p><img src="UJ.gif"/></p>
        <p>其中<img src="PDS.gif"/>是直接测量的偏导数。</p>
        <p>间接测量的结果表示为<img src="UJJ1.gif"/><img src="UJJ2.gif"/></p>
        <table>
          <tr>
            <td>请输入参数个数：</td><td><el-input type="text" v-model="answer"></el-input></td>
          </tr>
          <tr>
            <td><el-button type="warning">计算</el-button></td><td></td>
          </tr>
        </table>

        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>计算结果：</span>
            <!--        <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
          </div>
          <div>
            <table>
              <tr>
                <td ><a style="color: #f41776">间接不确定度：	</a></td><td><el-input :disabled="true" v-model="result[0]"></el-input></td>
              </tr>
              <tr>
                <td><a style="color: #f41776">相对不确定度：</a></td><td><el-input :disabled="true" type="text" v-model="result[1]"></el-input></td>
              </tr>
            </table>
          </div>
        </el-card>
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
