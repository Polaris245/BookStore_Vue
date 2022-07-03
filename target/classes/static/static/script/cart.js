new Vue({
    el: '#root',
    data: {
        cart: {
            totalCount:0,
            lastName:'',
            totalPrice:0,
            items: null
        },
        user: null,
        msg: '',
        confirmMsg:'',
        choose:0,
        temp:{
            a:1,
            b:1,
            c:1,
            item:null,
            flag: false,
        },
        dialogVisible:false
    },
    methods: {
        deleteItem(item) {
            this.msg = "您确定要从购物车中移除《" + item.name + "》这本书吗"
            this.confirmMsg = "确定删除"
            $("#modal2").modal("toggle")
            this.choose = 2
            this.temp.a = item.id
            console.log(item.id)
        },
        changeCount(e, item) {
            let check = /^[0-9]*[0-9][0-9]*$/
            if (!check.test(e.target.value)) {
                this.msg = "别闹，要输入正确的数哦"
                this.temp.item = item
                this.temp.c = item.count
                item.count = parseInt(e.target.value)
                this.temp.flag = true
                $("#modal1").modal("toggle")
            }
            else if (parseInt(e.target.value) !== item.count) {
                this.msg = "您确定要将《" + item.name + "》的数量修改为" + e.target.value + "吗"
                this.confirmMsg = "确定修改"
                this.temp.item = item
                this.temp.c = item.count
                item.count = parseInt(e.target.value)
                this.temp.flag = true
                $("#modal2").modal("toggle")
                this.choose = 0
                this.temp.a = item.id
                this.temp.b = e.target.value
            }
        },
        checkOut() {
            this.msg = "您确定要结算订单吗"
            this.confirmMsg = "确定结算"
            $("#modal2").modal("toggle")
            this.choose = 1
        },
        cancel() {
          if (this.temp.flag) {
              this.temp.item.count = this.temp.c
              this.temp.item = null
              this.temp.flag = false
          }
        },
        clear() {
            this.msg = "您确定要清空购物车吗"
            this.confirmMsg = "确定清空"
            $("#modal2").modal("toggle")
            this.choose = 4
        },
        async logout() {
            let response = await axios.get('/logout').catch(() => {
                this.msg = "网络异常"
                $("#modal1").modal("toggle")
            })
            if (response != null) {
                location.href = "/client/index.html"
            }
        },
        async sub(i) {
            $("#modal2").modal("hide")
            switch (i) {
                case 0:     //修改数量
                    let response = await axios.get('/cart/update?id=' + this.temp.a + '&count=' + this.temp.b).catch(() => {
                        this.msg = "网络异常"
                        $("#modal1").modal("toggle")
                    })
                    if (response != null && response.data.result) {
                        this.cart = response.data.cart
                    }
                    else if (response != null){
                        this.msg = response.data.msg
                        this.temp.item.count = this.temp.c
                        this.temp.item = null
                        this.temp.flag = false
                        $("#modal1").modal("toggle")
                    }
                    break;
                case 1:     //结算
                    let response2 = await axios.get('/cart/checkout').catch(() => {
                        this.msg = "网络异常"
                        $("#modal1").modal("toggle")
                    })
                    if (response2 != null && response2.data.result) {
                        this.cart = response2.data.cart
                        this.msg = "结算成功，您的订单号是" + response2.data.orderId
                        this.choose = 3
                        this.dialogVisible = true
                    }
                    else if (response2 != null){
                        this.msg = response2.data.msg
                        $("#modal1").modal("toggle")
                    }
                    break;
                case 2:     //删除
                    let response3 = await axios.get('/cart/delete?id=' + this.temp.a).catch(() => {
                        this.msg = "网络异常"
                        $("#modal1").modal("toggle")
                    })
                    if (response3 != null && response3.data.result) {
                        this.cart = response3.data.cart
                    }
                    break;
                case 3:     //跳转
                    this.dialogVisible = false
                    location.href = "/client/order.html"
                    break;
                case 4:     //清空
                    let response4 = await axios.get('/cart/clear').catch(() => {
                        this.msg = "网络异常"
                        $("#modal1").modal("toggle")
                    })
                    if (response4 != null && response4.data.result) {
                        this.cart = response4.data.cart
                    }
            }
        }
    },
    async created() {
        let response = await axios.get('/cart').catch(error => {
            this.msg = "网络异常"
            $("#modal1").modal("toggle")
        })
        if (response != null && response.data) {
            this.cart = response.data.cart
        }
        response = await axios.get('/login/isSignIn').catch(() => {
            this.msg = "网络异常"
            $("#modal").modal("toggle")
        })
        if (response != null && response.data.result) {
            this.user = response.data.user
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
    }
})