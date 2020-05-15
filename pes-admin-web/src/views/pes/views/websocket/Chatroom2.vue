<template>
  <div>

<!--    <input type="text" v-model="Msg.chatValue">-->
    <el-input v-model="Msg.toUserId" placeholder="请输入对方学号（用户名）进行聊天" @change="filterFromName()"></el-input>
<!--    <input type="text" v-model="Msg.toUserId" @change="filterFromName()">-->
<!--    <button @click="sendMessage">发送消息</button>-->
<!--    <div class="bubble">-->
<!--    </div>-->
<!--    <div>-->
<!--      <div v-for="(msg,key) in datas" :key="key">-->
<!--        {{msg}}-->
<!--      </div>-->
<!--    </div>-->

    <div class="chat-box">
      <header>聊天室</header>
      <div class="msg-box" ref="msg-box">
        <div
          v-for="(i,index) in showData"
          :key="index"
          class="msg"
          :style="i.fromName == Msg.fromName?'flex-direction:row-reverse':''"
        >
          <div class="user-head">
            <img :src="i.avatar" height="30" width="30" :title="i.fromName">
          </div>

          <div class="user-msg">
            <span :style="i.fromName == Msg.fromName?' float: right;':''" :class="i.fromName == Msg.fromName?'right':'left'">{{i.chatValue}}</span>
          </div>
        </div>
      </div>
      <div class="input-box">
        <input type="text" ref="sendMsg" v-model="Msg.chatValue" @keyup.enter="sendMessage" />
        <div class="btn" :class="{['btn-active']:Msg.chatValue}" @click="sendMessage">发送</div>
      </div>
    </div>


  </div>
</template>
<script>
  import SockJS from 'sockjs-client'
  import Stomp from 'webstomp-client'
  import { getInfo } from '@/api/login'
  import Env from  '../../../../../config/prod.env'

  export default {
    name: 'ChatRoom2',
    data() {
      return {
        text: '',
        Msg: {
          fromName: '',
          chatValue: '',
          toUserId: ''
        },
        datas: [],
        showData: [],
        stompClient: null
      }
    },
    mounted() {
      if ('WebSocket' in window) {
        this.initWebSocket()
      } else {
        alert('当前浏览器 Not support websocket')
      }
      this.$refs['msg-box'].scrollTop=this.$refs['msg-box'].scrollHeight
    },
    methods: {
      sendMessage() {
        //this.stompTopicNow()
        this.stompClient.send('/alongRequest', JSON.stringify(this.Msg), {})
        this.showData.push(JSON.parse(JSON.stringify(this.Msg)))
        //this.$refs['msg-box'].scrollTop=this.$refs['msg-box'].scrollHeight

      },
      initWebSocket() {
        getInfo().then(res=>{
          this.Msg.fromName=res.username
          this.connection()
        })

      },
      connection() {
        const socket = new SockJS(Env.BASE_API.replace(/"/g,"") + '/endpointMark')
        this.stompClient = Stomp.over(socket)
        this.stompClient.connect({}, (frame) => {
          console.log("connect"+frame);
          this.stompTopic();
        })
      }
      ,
      stompTopic(){
        this.stompClient.subscribe('/queue/'+this.Msg.fromName+'/alone',(res)=>{
          console.log(res)
          var message=  JSON.parse(res.body);
          this.datas.push(...message)

        })
      },
      filterFromName(){
        console.log("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
       // console.log(this.datas)
        this.showData=this.datas.filter(msg=>
           (msg.fromName==this.Msg.fromName.toString() && msg.toUserId==this.Msg.toUserId.toString())  ||  (msg.fromName==this.Msg.toUserId.toString() && msg.toUserId==this.Msg.fromName.toString())
        )
        //console.log(this.showData)
        this.$refs['msg-box'].scrollTop=this.$refs['msg-box'].scrollHeight
      }


    }

    ,
    updated() {
      //this.$refs['msg-box'].scrollTop=this.$refs['msg-box'].scrollHeight
    }
  }
</script>

<style lang="scss" scoped>
  .chat-box {
    margin: 20px 20px 20px 20px;
    background: #fafafa;
    position: absolute;
    height: 80%;
    width: 100%;
    max-width: 700px;
    header {
      position: fixed;
      width: 100%;
      height: 3rem;
      background: #409eff;
      max-width: 700px;
      display: flex;
      justify-content: center;
      align-items: center;
      font-weight: bold;
      color: white;
      font-size: 1rem;
    }
    .msg-box {
      position: absolute;
      height: calc(100% - 6.5rem);
      /*height: 100%;*/
      width: 100%;
      margin-top: 3rem;
      overflow-y: scroll;
      .msg {
        width: 95%;
        min-height: 2.5rem;
        margin: 1rem 0.5rem;
        position: relative;
        display: flex;
        justify-content: flex-start !important;
        .user-head {
          min-width: 2.5rem;
          padding-left: 5px;
          padding-right: 5px;
          width: 20%;
          width: 2.5rem;
          height: 2.5rem;
          border-radius: 50%;
          background: #f1f1f1;
          display: flex;
          justify-content: center;
          align-items: center;
          .head {
            width: 1.2rem;
            height: 1.2rem;
          }
          // position: absolute;
        }
        .user-msg {
          width: 75%;
          padding-left: 5px;
          padding-right: 5px;
          // position: absolute;
          word-break: break-all;
          position: relative;
          z-index: 5;
          span {
            display: inline-block;
            padding: 0.5rem 0.7rem;
            border-radius: 0.5rem;
            margin-top: 0.2rem;
            font-size: 0.88rem;
          }
          .left {
            background: white;
            animation: toLeft 0.5s ease both 1;
          }
          .right {
            background: #53a8ff;
            color: white;
            animation: toright 0.5s ease both 1;
          }
          @keyframes toLeft {
            0% {
              opacity: 0;
              transform: translateX(-10px);
            }
            100% {
              opacity: 1;
              transform: translateX(0px);
            }
          }
          @keyframes toright {
            0% {
              opacity: 0;
              transform: translateX(10px);
            }
            100% {
              opacity: 1;
              transform: translateX(0px);
            }
          }
        }
      }
    }
    .input-box {
      padding: 0 0.5rem;
      position: absolute;
      bottom: 0;
      width: 100%;
      height: 3.5rem;
      background: #fafafa;
      box-shadow: 0 0 5px #ccc;
      display: flex;
      justify-content: space-between;
      align-items: center;
      input {
        height: 2.3rem;
        display: inline-block;
        width: 100%;
        padding: 0.5rem;
        border: none;
        border-radius: 0.2rem;
        font-size: 0.88rem;
      }
      .btn {
        height: 2.3rem;
        min-width: 4rem;
        background: #e0e0e0;
        padding: 0.5rem;
        font-size: 0.88rem;
        color: white;
        text-align: center;
        border-radius: 0.2rem;
        margin-left: 0.5rem;
        transition: 0.5s;
      }
      .btn-active {
        background: #409eff;
      }
    }
  }
</style>
