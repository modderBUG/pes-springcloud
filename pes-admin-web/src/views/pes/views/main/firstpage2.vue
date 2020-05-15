<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
      <el-col :span="16">
        <div class="grid-content bg-purple">


          <!--卡片 资料中心-->
          <el-card class="box-card" style="width: 49%;min-width: 490px; margin-right: 20px;float:left;">
            <div slot="header" class="clearfix">
              <span><i class="el-icon-folder-opened">&nbsp;</i>实验资料</span>
              <el-button style="float: right; padding: 3px 0" type="text">全部下载 <i class="el-icon-download"></i>
              </el-button>
            </div>
            <div class="margin1">
              <div v-for="o in 20" :key="o" class="text item">
                <i class="el-icon-document-copy">&nbsp;</i> <a href="">{{'光电效应实验讲义 ' + o }}</a><i
                class="el-icon-download" style="float: right"></i>
              </div>
            </div>
          </el-card>

          <el-tabs type="border-card" class="box-card " style="width: 49%;min-width: 490px;float:left; "
                   v-if="this.loginStatus==0">
            <el-tab-pane>
              <span slot="label"><i class="el-icon-user"></i> 学生登录</span>
              <div class="text item margin0" style="margin: 0">
                <el-form :model="loginForm" :rules="loginRules" ref="loginForm" label-width="40px" class="login-form">

                  <el-form-item prop="username">
                    <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="学号">
                      <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>
                  <el-form-item prop="password">
                    <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="密码"
                              @keyup.enter.native="handleLogin">
                      <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>


                  <el-form-item prop="code">
                    <el-input v-model="loginForm.code" auto-complete="off" placeholder="验证码" style="width: 63%"
                              @keyup.enter.native="handleLogin">
                      <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
                    </el-input>
                    <div class="login-code">
                      <img :src="codeUrl" @click="getCode">
                    </div>
                  </el-form-item>


                  <el-form-item style="margin: 0">
                    <el-button :loading="loading" size="medium" type="primary" style="width:100%;"
                               @click.native.prevent="handleLogin">
                      <span v-if="!loading">登 录</span>
                      <span v-else>登 录 中...</span>
                    </el-button>
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
                <el-form :model="loginForm" :rules="loginRules" ref="loginForm" label-width="40px" class="login-form">

                  <el-form-item prop="username">
                    <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="工号">
                      <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>
                  <el-form-item prop="password">
                    <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="密码"
                              @keyup.enter.native="handleLogin">
                      <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>


                  <el-form-item prop="code">
                    <el-input v-model="loginForm.code" auto-complete="off" placeholder="验证码" style="width: 63%"
                              @keyup.enter.native="handleLogin">
                      <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
                    </el-input>
                    <div class="login-code">
                      <img :src="codeUrl" @click="getCode">
                    </div>
                  </el-form-item>


                  <el-form-item style="margin: 0">
                    <el-button :loading="loading" size="medium" type="primary" style="width:100%;"
                               @click.native.prevent="handleLogin">
                      <span v-if="!loading">登 录</span>
                      <span v-else>登 录 中...</span>
                    </el-button>
                    <p>
                      忘记密码啊？
                      <router-link to='/register'>立即找回</router-link>
                    </p>
                  </el-form-item>
                </el-form>
              </div>
            </el-tab-pane>
          </el-tabs>

          <div style="display: inline-block;">

            <el-card type="border-card" class="box-card " style="width: 23%;min-width: 250px;float:left; height: 100%;"
                     v-if="this.loginStatus==1">
              <div slot="header" class="clearfix">
                <span>用户信息</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="open()">登出</el-button>
              </div>
              <div class="text item" style="margin-left: 10%"><img
                :src="userInfo.avatar ? baseApi + '/avatar/' + userInfo.avatar : Avatar" title="点击上传头像" class="avatar">
              </div>
              <div class="text item" style="margin-left: 10%">姓名：{{userInfo.username }}</div>
              <div class="text item" style="margin-left: 10%">邮箱：{{userInfo.email }}</div>
              <div class="text item" style="margin-left: 10%">职位：{{userInfo.job}}</div>
              <div class="text item" style="margin-left: 10%">部门：{{userInfo.dept}}</div>
            </el-card>

            <el-card type="border-card" class="box-card "
                     style="width: 20%;min-width: 250px;float:left;margin-left: 10px; height: 100%;"
                     v-if="this.loginStatus==1">
              <div slot="header" class="clearfix">
                <span>快接入口</span>
                <el-button style="float: right; padding: 3px 0" type="text"></el-button>
              </div>
              <div class="text item" style="margin-left: 10%;margin: 30px 10px 25px 10px;">
                <el-link type="success">
                  <router-link to='/dashboard'>进入后台</router-link>
                </el-link>
              </div>
              <div class="text item" style="margin-left: 10%;margin: 30px 10px 25px 10px;">
                <el-link type="success">
                  <router-link to='/user/center'>进入选课</router-link>
                </el-link>
              </div>
              <div class="text item" style="margin-left: 10%;margin: 30px 10px 25px 10px;">
                <el-link type="success">
                  <router-link to='/user/center'>查看课表</router-link>
                </el-link>
              </div>
              <div class="text item" style="margin-left: 10%;margin: 30px 10px 25px 10px;">
                <el-link type="success">
                  <router-link to='/user/center'>个人中心</router-link>
                </el-link>
              </div>
              <div class="text item" style="margin-left: 10%;margin: 30px 10px 35px 10px;">
                <el-link type="success"><a @click="open()"> 退出登录</a></el-link>
              </div>
            </el-card>
          </div>


        </div>


      </el-col>
      <el-col :span="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {encrypt} from '@/utils/rsaEncrypt'
  import Config from '@/config'
  import {getCodeImg, getInfo} from '@/api/login'
  import Cookies from 'js-cookie'
  import {getToken} from '@/utils/auth' // getToken from cookie
  import {parseTime} from "../../../../utils/index";
  import Avatar from '@/assets/avatar/avatar.png'
  import {mapGetters} from 'vuex'

  export default {
    name: "firstpage",
    data() {
      return {
        Avatar: Avatar,
        codeUrl: '',
        cookiePass: '',
        loginForm: {
          username: 'admin',
          password: '123456',
          rememberMe: false,
          code: '',
          uuid: ''
        },
        loginStatus: 0,
        loginRules: {
          username: [{required: true, trigger: 'blur', message: '用户名不能为空'}],
          password: [{required: true, trigger: 'blur', message: '密码不能为空'}],
          code: [{required: true, trigger: 'change', message: '验证码不能为空'}]
        },
        loading: false,
        redirect: undefined,
        userInfo: {
          username: '',
          avatar: '',
          email: '',
          phone: '',
          dept: '',
          job: '',
          createTime: '',
        }
      }
    },
    computed: {
      ...mapGetters([
        'user',
        'updateAvatarApi',
        'baseApi'
      ])
    },
    created() {
      this.getCode()
      this.getCookie()
      this.loginCheck()
    },
    methods: {

      open() {
        this.$confirm('确定注销并退出系统吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.logout()
        })
      },
      logout() {

        this.$store.dispatch('LogOut').then(() => {
          location.reload() // 为了重新实例化vue-router对象 避免bug
        })
      },


      parseTime,
      loginCheck() {
        if (getToken()) {
          this.loginStatus = 1


          getInfo().then(res => {
            this.userInfo = res;
          })
        } else {
          this.loginStatus = 0
        }
      },
      getCode() {
        getCodeImg().then(res => {
          this.codeUrl = res.img
          this.loginForm.uuid = res.uuid
        })
      },
      getCookie() {
        const username = Cookies.get('username')
        let password = Cookies.get('password')
        const rememberMe = Cookies.get('rememberMe')
        // 保存cookie里面的加密后的密码
        this.cookiePass = password === undefined ? '' : password
        password = password === undefined ? this.loginForm.password : password
        this.loginForm = {
          username: username === undefined ? this.loginForm.username : username,
          password: password,
          rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
          code: ''
        }
      },
      handleLogin() {
        this.$refs.loginForm.validate(valid => {
          const user = {
            username: this.loginForm.username,
            password: this.loginForm.password,
            rememberMe: this.loginForm.rememberMe,
            code: this.loginForm.code,
            uuid: this.loginForm.uuid
          }
          if (user.password !== this.cookiePass) {
            user.password = encrypt(user.password)
          }
          if (valid) {
            this.loading = true
            if (user.rememberMe) {
              Cookies.set('username', user.username, {expires: Config.passCookieExpires})
              Cookies.set('password', user.password, {expires: Config.passCookieExpires})
              Cookies.set('rememberMe', user.rememberMe, {expires: Config.passCookieExpires})
            } else {
              Cookies.remove('username')
              Cookies.remove('password')
              Cookies.remove('rememberMe')
            }
            this.$store.dispatch('Login', user).then(() => {
              this.loading = false
              this.$router.push({path: this.redirect || '/pesindex'})
              location.reload();
            }).catch(() => {
              this.loading = false
              this.getCode()
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      }
    }
  }
</script>

<style scoped lang="scss">
  .margin0 {
    margin: 0;
    border: 0;
  }

  .margin1 {
    height: 280px;
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

    img {
      cursor: pointer;
      vertical-align: middle
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

  .avatar {
    width: 120px;
    height: 120px;
    display: block;
    border-radius: 50%
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
