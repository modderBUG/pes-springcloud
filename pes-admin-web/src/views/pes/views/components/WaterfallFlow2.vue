<template>
  <!-- https://github.com/lfyfly/vue-waterfall-easy/blob/master/README-CN.md   -->
  <vue-waterfall-easy :imgsArr="imgsArr" @scrollReachBottom="getData" ref="waterfall">
    <div class="img-info" slot-scope="props">
      <p class="some-info">第{{props.index+1}}张图片</p>
      <p class="some-info">{{props.value.info}}</p>

<!--      <div slot="loading" v-if="">first-loading...</div>-->
<!--      <div v-else>loading...</div>-->
      <div slot="waterfall-over"></div>
    </div>

  </vue-waterfall-easy>
</template>

<script>
  //import vueWaterfallEasy from '/vue-waterfall-easy.vue'
  import vueWaterfallEasy from 'vue-waterfall-easy'
  import axios from 'axios/index'
  import {getAllPic,getAllPic2,getPicturesByIndex} from "../../api/WaterFall";

  export default {
    name: 'app',
    data() {
      return {
        imgsArr: [],
        group: 0, // 当前加载的加载图片的次数
        pullDownDistance: 0
      }
    },
    components: {
      vueWaterfallEasy
    },
    methods: {
      getData() {
        // axios.get('./static/mock/data.json?group=' + this.group) // 真实环境中，后端会根据参数group返回新的图片数组，这里我用一个静态json文件模拟
        //   .then(res => {
        //     this.group++
        //     if (this.group === 10) { // 模拟已经无新数据，显示 slot="waterfall-over"
        //       this.$refs.waterfall.waterfallOver()
        //       return
        //     }
        //     this.imgsArr = this.imgsArr.concat(res.data)
        //   })
        getPicturesByIndex(this.group).then(res => {
          this.group++
          if (res == null ||res == undefined||res == "") { // 模拟已经无新数据，显示 slot="waterfall-over"
            console.log(this.$refs.waterfall);
            this.$refs.waterfall.waterfallOver()
            console.log("结束了");

            return
          }

          // if (this.group%3==0&&this.group!=0) {
          //   this.imgsArr=this.imgsArr.slice(this.group*20,this.imgsArr.length)
          // }
          this.imgsArr = this.imgsArr.concat(res)

        })

        console.log(this.group);

      },
      clickFn(event, {index, value}) {
        // event.preventDefault()
        if (event.target.tagName.toLowerCase() == 'img') {
          console.log('img clicked', index, value)
        }
      },
      imgErrorFn(imgItem) {
        console.log('图片加载错误', imgItem)
      },
      changeImgArr() {
     //   axios.get('./static/mock/data-change.json') // 真实环境中，后端会根据参数group返回新的图片数组，这里我用一个静态json文件模拟
        getAllPic2().then(res => {
            this.imgsArr = res
          })
      },
      pullDownMove(pullDownDistance) {
        // console.log('pullDownDistance', pullDownDistance)
        this.pullDownDistance = pullDownDistance
      },
      pullDownEnd(pullDownDistance) {
        console.log('pullDownEnd')
        if (this.pullDownDistance > 50) {
          alert('下拉刷新')
        }
        this.pullDownDistance = 0
      },
    },
    created() {

      this.getData()

      // 删除某个卡片
      // setTimeout(()=>{
      //   this.imgsArr.splice(1,1)
      // },2000)
    },
  }
</script>

<style lang="scss">
  * {
    margin: 0;
    padding: 0;
  }

  a {
    color: #000;
    text-decoration: none;

    &:active {
      color: #000;
    }
  }

  html,
  body,
  #app {
    height: 100%;
  }

  #app {
    position: relative;

    #header {
      display: block;
      text-align: center;
      background: #000;
      color: #cccccc;
      line-height: 32px;
      font-size: 16px;
      font-weight: bold;
      letter-spacing: 2px;
      position: fixed;
      z-index: 999;
      width: 100%;
    }

    #content {
      position: absolute;
      top: 32px;
      bottom: 0;
      width: 100%;
    }
  }

  // .__err__ .img-wraper {
  //   background: url(/static/img/1.jpg) no-repeat center/100px 100px !important;
  // }
  #app {
    overflow: auto;
    position: relative;

    .some-info {
      line-height: 1.6;
      text-align: center;
    }
  }
</style>

