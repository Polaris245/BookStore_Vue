new Vue({
    el: '#root',
    data: {
        page: {
            items:null
        },
        msg: '',
        temp: {
            a:''
        },
        choose: 1,
        jump:'',
        confirmMsg:''
    },
    methods: {
        status(status) {
            if (status === 0) return "待发货"
            if (status === 1) return "已发货"
            if (status === 2) return "已完成"
        },
        orderDetail(id) {
            location.href = "/client/orderitem.html?id=" + id
        },
        deleteOrder(id) {
            this.msg = "您确定要删除这个订单吗"
            this.confirmMsg = "确定删除"
            $("#modal2").modal("toggle")
            this.choose = 1
            this.temp.a = id
        },
        sendGoods(id) {
            this.msg = "确认发货吗？"
            this.confirmMsg = "确定"
            $("#modal2").modal("toggle")
            this.choose = 0
            this.temp.a = id
        },
        async sub(i) {
            $("#modal2").modal("hide")
            switch (i) {
                case 0:     //更新状态
                    let response = await axios.get('/order/update?status=0&id=' + this.temp.a + "&pageNo=" + this.page.pageNo).catch(() => {
                        this.msg = "网络异常"
                        $("#modal1").modal("toggle")
                    })
                    if (response != null && response.data.result) {
                        this.page = response.data.page
                    }
                    else if (response != null) {
                        this.msg = response.data.msg
                        $("#modal1").modal("toggle")
                    }
                    break;
                case 1:     //删除
                    let response2 = await axios.get('/order/delete?id=' + this.temp.a + "&pageNo=" + this.page.pageNo).catch(() => {
                        this.msg = "网络异常"
                        $("#modal1").modal("toggle")
                    })
                    if (response2 != null && response2.data.result) {
                        this.page = response2.data.page
                    }
                    else if (response2 != null) {
                        this.msg = response2.data.msg
                        $("#modal1").modal("toggle")
                    }
                    break;
            }
        },
        async jumpPage(){
            let check = /^[0-9]*[1-9][0-9]*$/
            if (!check.test(this.jump)) {
                this.msg = "别闹，输入点正常的数"
                $("#modal1").modal("toggle")
            }
            else {
                let response = await axios.get(this.page.url + 'pageNo=' + this.jump).catch(() => {
                    this.msg = "通信异常"
                    $("#modal1").modal("toggle")
                })
                if (response != null && response.data.result) {
                    this.page = response.data.page
                }
            }
        },
        async jumpTo(i) {
            let response = await axios.get(this.page.url + 'pageNo=' + i).catch(() => {
                this.msg = "通信异常"
                $("#modal1").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.page = response.data.page
            }
        },
        async jumpToIndex() {
            let response = await axios.get(this.page.url + 'pageNo=1').catch(() => {
                this.msg = "通信异常"
                $("#modal1").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.page = response.data.page
            }
        },
        async jumpToEnd() {
            let response = await axios.get(this.page.url + 'pageNo=' + this.page.pageTotal).catch(() => {
                this.msg = "通信异常"
                $("#modal1").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.page = response.data.page
            }
        },
        async nextPage() {
            let a = this.page.pageNo + 1
            let response = await axios.get(this.page.url + 'pageNo=' + a).catch(() => {
                this.msg = "通信异常"
                $("#modal1").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.page = response.data.page
            }
        },
        async lastPage() {
            let a = this.page.pageNo - 1
            let response = await axios.get(this.page.url + 'pageNo=' + a).catch(() => {
                this.msg = "通信异常"
                $("#modal1").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.page = response.data.page
            }
        },
    },
    async created() {
        let response = await axios.get('/manager/order').catch(error => {
            this.msg = "网络异常"
            $("#modal1").modal("toggle")
        })
        if (response != null && response.data.result) {
            this.page = response.data.page
        }
        else if (response != null) {
            this.msg = response.data.msg
            $("#modal1").modal("toggle")
        }
    },
    updated() {
        const links=document.querySelectorAll('.nav li a');
        links.forEach(link=>{
            let letters=link.textContent.split('');
            link.textContent='';
            letters.forEach((letter,i)=>{
                let span=document.createElement('span');
                span.textContent=letter;
                span.dataset.text=letter;
                span.style.transitionDelay=i/15+'s';
                link.append(span);
            })
        })
    },
    filters: {
        timeFormat(value, str = "YYYY-MM-DD HH:mm:ss") {
            return dayjs(value).format(str)
        }
    }
})