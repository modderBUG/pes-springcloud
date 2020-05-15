<template>

  <div class="container">
    <el-divider content-position="left">编辑消息推送，注：可以把权限赋给学生来完成操作</el-divider>
    <el-card class="box-card">

      <div style="width: 50%; margin-left: 20%; margin-bottom: 30px;"><p style="text-align: center;font-size: 15px;">
        注意：这里的消息将会显示在首页，使所有访问到主页的人看到此内容。</p>
      </div>
      <el-form ref="form" :model="form" label-width="80px">

        <el-form-item label="推送内容">
          <el-input
            type="textarea"
            :autosize="{ minRows: 4, maxRows: 10}"
            placeholder="请输入推送内容内容"
            v-model="textarea2">
          </el-input>
        </el-form-item>

        <el-form-item label="作者">
          <el-input v-model="input" placeholder="编辑：朴老师"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" round @click="submit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-divider content-position="left">历史推送，保留前五条</el-divider>

    <template>
           <el-table
        :data="tableData"
        style="width: 80%">
        <el-table-column
          prop="updateTime"
          label="日期"
          width="180">
        </el-table-column>
        <el-table-column
          prop="editorAuthor"
          label="姓名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="editorContent"
          label="内容">
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)" icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>

  </div>
</template>


<script>
import {getAll,delOne,addOne} from '../../api/PushMessage'
import {parseTime} from "../../../../utils/index";
  export default {
    name: "MessagePush",
    data() {
      return {
        input: '',
        textarea2: '',
        form: {
          name: ''
        },
        tableData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }]
      }
    },
    methods: {
      parseTime,
      submit() {
        var ser={
          editorContent:this.textarea2,
          editorAuthor:this.input
        }
        this.textarea2=''
        this.input=''
        addOne(ser).then(res=>{
          this.$message({
            message: '添加成功',
            type: 'success'
          });
          console.log(res)
          this.getInitData()
        })


      },
      handleDelete(index, row) {
        delOne(row.id).then(res=>{
          this.$message({
            message: '添加成功',
            type: 'warning'
          });
          console.log(res)
          this.getInitData()
        })

      },
      getInitData(){
        let args='?limit=15';
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
  .container {
    margin: 20px 20px 20px 20px;
  }


  .box-card {
    width: 480px;
  }

</style>
