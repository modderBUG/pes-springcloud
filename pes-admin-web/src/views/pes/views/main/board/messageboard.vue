<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
      <el-col :span="16">
        <!--        这里存放留言板组件-->
        <msglist :texthtml="texthtml"></msglist>

      </el-col>
      <el-col :span="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
      <el-col :span="16">

        <Editor @func="getMsgFormSon" ref="editor"></Editor>


        <el-row>
          <!--          <div class="login-code">-->
          <!--            <img :src="codeUrl" @click="getCode">-->
          <!--          </div>-->
          <!--          <el-input v-model="loginForm.code" auto-complete="off" placeholder="验证码" style="width: 20%"-->
          <!--                    @keyup.enter.native="handleLogin">-->
          <!--            <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>-->
          <!--          </el-input>-->
          <el-button type="primary" plain style="float: right;" @click="sendText()">发送</el-button>
          <el-link v-if="this.loginStatus==0" type="danger" href="/">您还没有登录，不能进行留言发表，点击登录！</el-link>
        </el-row>

      </el-col>
      <el-col :span="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import Editor from '../../../../components/Editor'
  import msglist from '../board/listcomponents'
  import { getToken } from '@/utils/auth' // getToken from cookie

  export default {
    components: {Editor, msglist},
    name: "messageboard",
    data() {
      return {
        loginForm: {
          username: 'admin',
          password: '123456',
          rememberMe: false,
          code: '',
          uuid: ''
        },
        texthtml: {},
        loginStatus:0,
      }
    },
    methods: {

      getMsgFormSon(data) {
        var utf8char=data.toString().match(/[\u4e00-\u9fa5]/g)
        if (utf8char==null) utf8char='' ;
        if(data.toString().length>500||utf8char.length>200){
          this.$notify({
            title: '字数超过限制,200汉字或500字符以内',
            type: 'warning',
            duration: 2500,
            //data: msg,
          })
          return
        }
        var msgobj =
          {
            editorContent: data,
          }

        console.log(msgobj)
        this.texthtml=msgobj
      }
      ,
      sendText() {
        this.$refs.editor.sendMsg()
      },
      loginCheck(){
        if (getToken()){
          this.loginStatus=1
        } else {
          this.loginStatus=0
        }
      }
    },
    created() {
    this.loginCheck()
    }
  }
</script>

<style scoped lang="scss">
  .login-code {
    width: 15%;
    display: inline-block;
    height: 38px;


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
