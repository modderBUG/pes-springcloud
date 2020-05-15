<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
      <el-col :span="8">
        <div class="grid-content bg-purple">

          <!--卡片 资料中心-->
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span><i class="el-icon-folder-opened">&nbsp;</i>实验资料</span>
              <el-button style="float: right; padding: 3px 0" type="text">全部下载 <i class="el-icon-download"></i>
              </el-button>
            </div>
            <div class="margin1">
            <div v-for="o in 20" :key="o" class="text item">
              <i class="el-icon-document-copy">&nbsp;</i> <a href="">{{'光电效应实验讲义 ' + o }}</a><i class="el-icon-download" style="float: right"></i>
            </div>
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="grid-content bg-purple">

          <!--&lt;!&ndash;        卡片 资料中心&ndash;&gt;-->
          <!--        <el-card class="box-card">-->
          <!--          <div slot="header" class="clearfix"><i class="el-icon-user"></i>-->
          <!--            <span>登录</span>-->
          <!--&lt;!&ndash;            <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>&ndash;&gt;-->
          <!--          </div>-->

          <!--        </el-card>-->

          <!--        卡片 资料中心-->

          <!--              <el-tabs v-model="activeName" type="card" @tab-click="handleClick">-->
          <!--                <el-tab-pane label="用户管理" name="first" style="width: 50%">用户管理</el-tab-pane>-->
          <!--                <el-tab-pane label="配置管理" name="second">配置管理</el-tab-pane>-->
          <!--              </el-tabs>-->

          <el-tabs type="border-card" class="box-card">
            <el-tab-pane>
              <span slot="label"><i class="el-icon-user"></i> 学生登录</span>
              <div class="text item margin0" style="margin: 0">
                <el-form :model="loginUser" :rules="rules" ref="loginForm" label-width="40px" class="login-form">

                  <el-form-item prop="username">
                    <el-input v-model="loginUser.email" type="text" auto-complete="off" placeholder="学号">
                      <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>
                  <el-form-item prop="password">
                    <el-input v-model="loginUser.password" type="password" auto-complete="off" placeholder="密码"
                              @keyup.enter.native="handleLogin">
                      <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>


                  <el-form-item prop="code">
                    <el-input v-model="loginUser.code" auto-complete="off" placeholder="验证码" style="width: 63%"
                              @keyup.enter.native="handleLogin">
                      <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
                    </el-input>
                    <div class="login-code">
                      <img :src="codeUrl" @click="getCode">
                    </div>
                  </el-form-item>


                  <el-form-item style="margin: 0">
                    <el-button class="submit_btn" type="primary" @click="submitForm('loginForm')">登录</el-button>&nbsp;&nbsp; <a style="color:red;">验证码错误</a>
                    <p>
                      忘记密码啊？
                      <router-link to='/register'>立即找回</router-link>
                    </p>
                  </el-form-item>

                </el-form>
              </div>
            </el-tab-pane>
            <el-tab-pane>
              <span slot="label"><i class="el-icon-user-solid"></i> 教师登录</span>
              <div class="text item" style="margin: 0">
                <el-form :model="loginUser" :rules="rules" ref="loginForm" label-width="40px" class="login-form">

                  <el-form-item prop="username">
                    <el-input v-model="loginUser.email" type="text" auto-complete="off" placeholder="工号">
                      <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>
                  <el-form-item prop="password">
                    <el-input v-model="loginUser.password" type="password" auto-complete="off" placeholder="密码"
                              @keyup.enter.native="handleLogin">
                      <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>


                  <el-form-item prop="code">
                    <el-input v-model="loginUser.code" auto-complete="off" placeholder="验证码" style="width: 63%"
                              @keyup.enter.native="handleLogin">
                      <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
                    </el-input>
                    <div class="login-code">
                      <img :src="codeUrl" @click="getCode">
                    </div>
                  </el-form-item>


                  <el-form-item style="margin: 0">
                    <el-button class="submit_btn" type="primary" @click="submitForm('loginForm')">登录</el-button>&nbsp;&nbsp; <a style="color:red;">验证码错误</a>
                    <p>
                      忘记密码啊？
                      <router-link to='/register'>立即找回</router-link>
                    </p>
                  </el-form-item>
                </el-form>
              </div>
            </el-tab-pane>


          </el-tabs>


        </div>


      </el-col>
      <el-col :span="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "firstpage",
    data() {
      return {
        codeUrl: './tools/下载.png',
        loginUser: {
          email: '',
          password: '',
          code: '',
        },
      }
    },
  }
</script>

<style scoped>
  .margin0{
    margin: 0;
    border: 0;
  }
  .margin1{
    height:280px;
    max-height: 285px;
    overflow: auto;
  }

  .login-form {
    border-radius: 0px;
    background: #ffffff;
    width: 80%;
    padding: 5px 5px 5px 5px;

  .el-input {
    height: 38px;

  input {
    height: 38px;
  }

  }
  .input-icon {
    height: 39px;
    width: 10px;
    margin-left: 2px;
  }

  }

  .login-code {
    width: 33%;
    display: inline-block;
    height: 38px;
    float: right;
    img{
      cursor: pointer;
      vertical-align:middle
    }
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
