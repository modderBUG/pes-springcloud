<template>
  <div>
    <el-divider></el-divider>

    <div class="list" v-for="(item,index) in list" :key="index">
      <div class="container">
        <div class="avatar">
          <el-avatar shape="square" :size="100"

                     :src="item.userAvatar.realName ? baseApi + '/avatar/' + item.userAvatar.realName : Avatar"></el-avatar>
          <div  style="text-align: center;"><el-link type="info" href="#">{{item.stuName}}</el-link></div>
        </div>
        <div style="display: inline-block; width: 80%;">
          <div class="editor-content" v-html="item.editorContent"/>
          <div style="float:right; margin-bottom: 20px;">
            <div >
              <el-link type="info" @click="handlerDisplayReply(index)" style="font-size: 20px;" icon="el-icon-chat-dot-square"></el-link>
              <el-link type="warning" @click="handlerDelete(item.id)" style="font-size: 20px;" icon="el-icon-delete"></el-link>
            </div>
            <div class="reply" v-show="displayList[index]" >
              <el-input
                type="textarea"
                placeholder="请输入内容"
                v-model="replyText"
                maxlength="30"
                show-word-limit
              >
              </el-input>
              <el-link type="primary" @click="handlerSubmitReply(item)" style="float: right;"  icon="el-icon-success">发送</el-link>
            </div>
          </div>
          <el-card class="box-card" v-if="item.reply.length!=0" style="margin-top: 20px;width: 60%;float: right;">
<!--            这里可以把{{item.reply}}}存入本地。然后取前几条放到这里遍历循环。设置翻页。-->
            <div v-for="(li,index) in item.reply" :key="index" class="text item">
              <el-link type="primary" href="#">{{li.stuName}}</el-link>：回复@{{li.toName}} {{li.editorContent}}
              <el-link type="primary" @click="handlerDeleteItem(li.id)" style="float: right;" icon="el-icon-delete"></el-link>
            </div>
          </el-card>

<!--          <div style="float:right;width:65%;height: 100%;overflow: auto;display: list-item">-->
<!--            <div style="" class="inline-reply" v-for="li in item.reply">-->
<!--              username:a-->
<!--            </div>-->

<!--          </div>-->
        </div>

      </div>{{parseTime(item.updateTime)}}
      <el-divider></el-divider>
    </div>

    <div style="text-align: center">
      <el-pagination background layout="prev, pager, next"
                     :total="page.count"
                     @current-change="handleCurrentChange"
                     @size-change="handleSizeChange"

      ></el-pagination>
    </div>
  </div>
</template>

<script>
  import {getAll, addOne, delOne, editOne, pageableGetAll,addOneReply,delOneReply,pageableGetAllData} from '../../../api/MessageApi'
  import Avatar from '@/assets/avatar/avatar.png'
  import {parseTime} from "../../../../../utils/index";
  import {mapGetters} from 'vuex'

  export default {
    name: "listcomponents",
    props: ['texthtml'],
    computed: {
      ...mapGetters([
        'user',
        'updateAvatarApi',
        'baseApi'
      ])
    },
    data() {
      return {
        page: {
          total: 20,
          size: 20,
          count: 20,
          currentPage:0
        },
        replyText:'',
        displayList:[]
        ,
        Avatar: Avatar,
        list: [
          {
            stuName: '',
            editorContent: ``,
            updateTime: '',
            userAvatar: {
              realName: ''
            },
            reply:[],
          }
         ],


      }
    },
    methods: {
      parseTime,

      handlerSubmitReply(item){
        const obj={
          toName:item.stuName,
          editorContent:this.replyText,
          commentId:item.id,
        }
        addOneReply(obj).then(res=>{
          console.log(res)
          this.handleCurrentChange(this.page.currentPage)
        }).catch(e=>{
          this.$message.error(e)
        })
      },
      handlerDeleteItem(id){
        delOneReply(id).then(res=>{
          console.log(res)
         this.handleCurrentChange(this.page.currentPage)
        }).catch(e=>{
          this.$message.error(e)
        })
      },



      handleCurrentChange(pageNum) {
        console.log(pageNum)
        this.page.currentPage=pageNum;
        pageableGetAllData(pageNum).then(res => {
          this.list = res.content
        })
      },
      handleSizeChange() {
        console.log(`当前页: ${val}`);
      },

      setInitData() {
        getAll().then(res => {
          this.list = res.content;
          this.page.total = res.totalPages;
          this.page.size = res.pageable.size;
          this.page.count = res.totalElements;
        }).catch(e => {

        })
      },

      submitItem(Item) {
        addOne(Item).then(res => {
          console.log(res)
          this.setInitData()
        })
      },

      handlerDisplayReply(index){
        if (this.displayList[index]!=true){
          this.displayList[index]=true;
        } else {
          this.displayList[index]=false;
        }
        this.$forceUpdate();
      }
      ,
      handlerDelete(id) {

        this.$confirm('删除本留言？').then(_ => {
          this.deleteItem(id)
        }).catch(e => {
          console.log("用户取消了操作")
        })

      }
      ,

      deleteItem(Item) {
        delOne(Item).then(res => {
          this.handleCurrentChange(this.page.currentPage)
        }).catch(e=>{
          this.$message.error(e);
        })
      }


    },
    created() {
      this.setInitData()
    }
    ,
    watch: {
      texthtml: {
        handler(newValue, oldValue) {
          //父组件param对象改变会触发此函数

          this.submitItem(newValue)
          this.setInitData()

          //this.list.push(newValue)
        },
        deep: true
      }

    },
  }
</script>

<style scoped>



  .inline-reply{
    height:30px;background-color:#EDEDED;
    margin-left: 5px;
  }
  .avatar {
    width: 120px;
    height: 120px;
    float: left;
    padding: 0px 10px 0px 10px;

  }

  .list {
  }

  .container {
    height: 100%;
    padding-bottom: 20px;
    margin-bottom: 0px;
    overflow: auto;

  }
</style>
