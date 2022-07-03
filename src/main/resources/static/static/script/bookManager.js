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
    },
    methods: {
        async changeBook(id) {
            location.href = "/manager/edit.html?id=" + id
        },
        deleteBook(book) {
            this.msg = "您确定要删除《" + book.name + "》这本书吗"
            this.temp.a = book.id
            $("#modal2").modal("toggle")
        },
        async deleteBookConfirm() {
            $("#modal2").modal("hide")
            let response = await axios.get('/manager/book/delete?id=' + this.temp.a + "&pageNo=" + this.page.pageNo).catch(error => {
                this.msg = "网络异常"
                $("#modal1").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.page = response.data.page
            }
            else {
                this.msg = response.data.msg
                $("#modal1").modal("toggle")
            }
        },
        async addBook() {
            location.href = "/manager/edit.html?id=new"
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
        let response = await axios.get('/manager/book').catch(error => {
            this.msg = "网络异常"
            $("#modal1").modal("toggle")
        })
        if (response != null && response.data.result) {
            this.page = response.data.page
        }
        else {
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
    }
})