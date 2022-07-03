const Test = Vue.extend({
    data() {
        return {
            jump:'',
            pageNo:'',
            url:'',
            msg:'',
            i: '',
            pageTotal:'',
            pageTotalCount: ''
        }
    },
    async created() {
        let response = await axios.get('/index').catch(error => {
            this.msg = "网络异常"
            $("#modal3").modal("toggle")
        })
        if (response != null && response.data.result) {
            this.pageNo = response.data.page.pageNo
            this.url = response.data.page.url
            this.pageTotal = response.data.page.pageTotal
            this.pageTotalCount = response.data.page.pageTotalCount
            this.$bus.$emit('showPage', response.data.page)
            this.$bus.$emit('showCart', response.data.cart)
        }
    },
    methods: {
        parseResponse(response) {
            this.pageNo = response.data.page.pageNo
            this.url = response.data.page.url
            this.pageTotal = response.data.page.pageTotal
            this.pageTotalCount = response.data.page.pageTotalCount
            this.$bus.$emit('showPage', response.data.page)
            this.$bus.$emit('showCart', response.data.cart)
        },
        async jumpPage() {
            let check = /^[0-9]*[1-9][0-9]*$/
            if (!check.test(this.jump)) {
                this.msg = "别闹，输入点正常的数"
                $("#modal3").modal("toggle")
            } else {
                let response = await axios.get(this.url + 'pageNo=' + this.jump).catch(() => {
                    this.msg = "通信异常"
                    $("#modal3").modal("toggle")
                })
                if (response != null && response.data.result) {
                    this.parseResponse(response)
                }
            }
        },
        async jumpTo(i) {
            let response = await axios.get(this.url + 'pageNo=' + i).catch(() => {
                this.msg = "通信异常"
                $("#modal3").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.parseResponse(response)
            }
        },
        async jumpToIndex() {
            let response = await axios.get(this.url + 'pageNo=1').catch(() => {
                this.msg = "通信异常"
                $("#modal3").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.parseResponse(response)
            }
        },
        async jumpToEnd() {
            let response = await axios.get(this.url + 'pageNo=' + this.pageTotal).catch(() => {
                this.msg = "通信异常"
                $("#modal3").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.parseResponse(response)
            }
        },
        async nextPage() {
            let a = this.pageNo + 1
            let response = await axios.get(this.url + 'pageNo=' + a).catch(() => {
                this.msg = "通信异常"
                $("#modal3").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.parseResponse(response)
            }
        },
        async lastPage() {
            let a = this.pageNo - 1
            let response = await axios.get(this.url + 'pageNo=' + a).catch(() => {
                this.msg = "通信异常"
                $("#modal3").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.parseResponse(response)
            }
        },
    },
    template:`<div id="page">
<div style="width: 80%; margin: 20px auto; display: flex; justify-content: center; align-items: center">
<nav aria-label="Page navigation example" style="width: fit-content; margin: 0 10px">
<ul class="pagination">
<template v-if="pageNo > 1">
<li class="page-item"><a class="page-link" href="" @click.prevent="jumpToIndex">首页</a></li>
<li class="page-item"><a class="page-link" href="" @click.prevent="lastPage">上一页</a></li>
</template>
<template v-if="pageTotal <= 5">
<template v-for="i in pageTotal">
<template v-if="i === pageNo">
<li class="page-item active"><a class="page-link" href="">{{i}}</a></li>
</template>
<template v-if="i !== pageNo">
<li class="page-item"><a class="page-link" href="" @click.prevent="jumpTo(i)">{{i}}</a></li>
</template>
</template>
</template>
<template v-if="pageTotal > 5">
<template v-if="pageNo <= 3">
<template v-for=" i in 5">
<template v-if="i === pageNo">
<li class="page-item active"><a class="page-link" href="">{{i}}</a></li>
</template>
<template v-if="i !== pageNo">
<li class="page-item"><a class="page-link" href="" @click.prevent="jumpTo(i)">{{i}}</a></li>
</template>
</template>
</template>
<template v-else-if="pageNo > pageTotal-3">
<template v-for="i in pageTotal" v-if="i >= pageTotal - 4">
<template v-if="i === pageNo">
<li class="page-item active"><a class="page-link" href="">{{i}}</a></li>
</template>
<template v-if="i !== pageNo">
<li class="page-item"><a class="page-link" href="" @click.prevent="jumpTo(i)">{{i}}</a></li>
</template>
</template>
</template>
<template v-else>
<template v-for="i in pageNo + 2" v-if="i >= pageNo - 2">
<template v-if="i === pageNo">
<li class="page-item active"><a class="page-link" href="">{{i}}</a></li>
</template>
<template v-if="i !== pageNo">
<li class="page-item"><a class="page-link" href="" @click.prevent="jumpTo(i)">{{i}}</a></li>
</template>
</template>
</template>
</template>
<template v-if="pageNo < pageTotal">
<li class="page-item"><a class="page-link" href="" @click.prevent="nextPage">下一页</a></li>
<li class="page-item"><a class="page-link" href="" @click.prevent="jumpToEnd">末页</a></li>
</template>
</ul>
</nav>
<div id="jump">
跳转到
<input type="number" class="form-control page_input" aria-label="Sizing example input"
aria-describedby="inputGroup-sizing-sm" v-model="jump"> 页
<button type="button" class="btn btn-outline-success btn-sm" @click="jumpPage">确定</button>
</div>
<div class="alert alert-success page_alert" role="alert">
共{{pageTotal}}页，{{pageTotalCount}}条记录
</div>
</div>
<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
<div class="modal-dialog modal-dialog-centered">
<div class="modal-content">
<div class="modal-header">
<h5 class="modal-title" id="exampleModalLabelInfo2">提示</h5>
<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
</div>
<div class="modal-body" style="text-align: center">
{{msg}}
</div>
<div class="modal-footer">
<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
</div>
</div>
</div>
</div>
</div>`
})