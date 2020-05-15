<template>
  <div >

    <el-row :gutter="20">
      <el-col :span="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
      <el-col :span="16">
        <div class="grid-content bg-purple">
          <!--走马灯-->
          <div class="block" style="min-width: 490px;">
            <!--      <span class="demonstration">默认 Hover 指示器触发</span>-->
            <el-carousel height="150px" style="text-align: center">
              <el-carousel-item v-for="(item,index) in imgitems" :key="index">
                <h3 class="small"><img :src="item" class="image"></h3>
              </el-carousel-item>
            </el-carousel>
          </div>

        </div>
      </el-col>
      <el-col :span="4" >
        <div class="grid-content bg-purple"></div>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
      <el-col :span="16">
        <div class="grid-content bg-purple" style="min-width: 490px;">
          <el-menu
            :default-active="activeIndex2"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b">

            <el-menu-item index="1">主页</el-menu-item>
            <!--      <el-menu-item index="3" disabled>消息中心</el-menu-item>-->
            <el-menu-item index="2">中心简介</el-menu-item>
            <el-menu-item index="3">实验工具箱</el-menu-item>
            <el-menu-item index="4">考勤系统说明</el-menu-item>
            <el-menu-item index="5">中心简介</el-menu-item>
            <el-menu-item index="6"><a href="" target="_blank">其他资源</a></el-menu-item>
            <el-menu-item index="7">留言板</el-menu-item>


          </el-menu>

        </div>
      </el-col>
      <el-col :span="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
    </el-row>

    <!--这里把组件拿过来过来-->
<!--    <firstpage v-if="showme=='firstpage'"></firstpage>-->
    <centerpage v-if="showme=='centerpage'"></centerpage>
    <toolspage  v-if="showme=='toolspage'"></toolspage>
    <infopage  v-if="showme=='infopage'"></infopage>
    <firstpage2  v-if="showme=='firstpage2'"></firstpage2>
    <messageboard  v-if="showme=='msgboard'"></messageboard>





    <el-row :gutter="20">
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="16">
        <div class="grid-content bg-purple">
        <el-tabs type="border-card" style="min-width: 490px;">
          <el-tab-pane label="消息中心">
            当有新消息发布时在这里显示
          <infopage></infopage></el-tab-pane>
          <el-tab-pane label="特色实验">特色实验</el-tab-pane>
          <el-tab-pane label="在线用户">角色管理</el-tab-pane>
          <el-tab-pane label="流量管理">定时任务补偿</el-tab-pane>
        </el-tabs>

      </div>
      </el-col>
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
    </el-row>


    <el-divider></el-divider>

    <el-row :gutter="20">
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="16"><div class="grid-content bg-purple">
        <el-card style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1) ;min-width: 490px;" >

          <table style="width: 100%" class="friendslink">
            <tr>
              <td> 友情链接</td> <td><el-link type="success">学校官网</el-link></td> <td><el-link type="success">教务在线</el-link></td> <td><el-link type="success">教育在线</el-link></td><td><el-link type="success">校园邮箱</el-link></td>
            </tr>
            <tr>
              <td></td> <td><el-link type="success">学校官网</el-link></td> <td><el-link type="success">学校官网</el-link></td> <td><el-link type="success">学校官网</el-link></td><td><el-link type="success">学校官网</el-link></td>
            </tr>
          </table>

        </el-card>
      </div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
    </el-row>



    <!--页面底部-->
    <el-row :gutter="10">
      <el-col :xs="8" :sm="6" :md="4" :lg="3" :xl="1">
        <div class="grid-content bg-purple"></div>
      </el-col>
      <el-col :xs="4" :sm="12" :md="16" :lg="18" :xl="22">
        <div class="grid-content bg-purple-light"><!--走马灯-->
          <div class="block" style="min-width: 490px;">
            <!--      <span class="demonstration">默认 Hover 指示器触发</span>-->
            <el-carousel height="150px" style="text-align: center">
              <el-carousel-item v-for="(item,index) in imgitems" :key="index">
                <h3 class="small"><img :src="item" class="image"></h3>
              </el-carousel-item>
            </el-carousel>
          </div>
        </div>
      </el-col>
      <el-col :xs="8" :sm="6" :md="4" :lg="3" :xl="1">
        <div class="grid-content bg-purple-light"></div>
      </el-col>
    </el-row>
<!--    <el-divider></el-divider>-->
    <el-footer class="myfooter"><p> 北京石油化工学院 物理实验网 </p></el-footer>
  </div>


</template>


<script>
  import firstpage from './firstpage'
  import firstpage2 from './firstpage2'
  import messageboard from './board/messageboard'
  // import centerpagevue from './centerpagevue'
import centerpage from './centerpage'
import toolspage from './toolspage'
import infopage from './infopage'
  export default {
    name: "pesindex",
    components:{firstpage,centerpage,toolspage,infopage,firstpage2,messageboard},


    data() {
      return {
        showme:'firstpage2',
        imgitems: [require("../../../../assets/PES_assets/images/header/1.gif"),
          require("../../../../assets/PES_assets/images/header/1.gif"),
          require("../../../../assets/PES_assets/images/header/1.gif"),
          require("../../../../assets/PES_assets/images/header/1.gif"),
        ],
        activeIndex: '1',
        activeIndex2: '1',
        loginUser: {
          email: '',
          password: ''
        },
      }
    },
    methods: {
      handleSelect(key, keyPath) {
       // console.log(key, keyPath);
        switch (key) {
          case '1':this.showme="firstpage2";break;
          case '2':this.showme="centerpage";break;
          case '3':this.showme="toolspage";break;
          case '4':this.showme="infopage";break;
          case '5':this.showme="firstpage2";break;
          case '6':this.showme="firstpage2";break;
          case '7':this.showme="msgboard";break;
          default:console.log(key, keyPath);
        }

      }
    },
  }
</script>


<style scoped>
  .friendslink{
    font-size: 20px;
    margin: 5px 5px 5px 5px;
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
