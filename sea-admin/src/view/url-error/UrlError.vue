<template>
  <div>
    <div class="search-con search-con-top">
      <Input clearable placeholder="错误信息" class="search-input" v-model="q"/>
      <Button @click="handleSearch" class="search-btn" type="primary">
        <Icon type="search"/>&nbsp;&nbsp;搜索
      </Button>
    </div>
    <BasicTable :columns="columns" :data="data.records" :size="data.size" :current="data.current" :total="data.total"
                @on-next-page="getNextPage" @on-page-size="handlePageSize"
                :showButton="true">

    </BasicTable>
  </div>
</template>
<script>
    import BasicTable from "@/components/tables"
    import {getTableData} from '@/api/urlError'

    export default {
        name: 'urlError',
        data() {
            return {
                columns: [{
                    title: '错误类型',
                    key: 'errorType'
                }, {
                    title: '错误号',
                    key: 'errorCode'
                }, {
                    title: '地址',
                    key: 'url'
                }, {
                    title: '错误消息',
                    key: 'msg'
                }, {
                    title: '错误时间',
                    key: 'createDate'
                }, {
                    title: '更新时间',
                    key: 'updateDate'
                }],
                data: {
                    records: [],
                    size: 10,
                    current: 1,
                    total: 0,
                    pages: 1
                },
                q: '' // 输入框
            }
        },
        components: {
            BasicTable
        },
        methods: {
            handleSearch() {
                this.getData();
            },
            getNextPage(next) {
                this.data.current = next;
                this.getData();
            },
            handlePageSize(index) {
                this.data.size = index;
                this.getData();
            },
            /**
             * 获取table数据.
             */
            getData() {
                const data = {
                    q: this.q,
                    size: this.data.size,
                    current: this.data.current
                };
                getTableData(data).then(res => {
                    this.data = res.data;
                }).catch(err => {
                    console.log(err);
                });
            },

        },
        mounted() {
            this.getData();
        }
    }
</script>
<style scoped>

</style>
