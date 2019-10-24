<template>
  <div>
    <div class="search-con search-con-top">
      <Input clearable placeholder="昵称/账号" class="search-input" v-model="q"/>
      <Button @click="handleSearch" class="search-btn" type="primary">
        <Icon type="search"/>&nbsp;&nbsp;搜索
      </Button>
    </div>
    <BasicTable :columns="columns" :data="data.records" :size="data.size" :current="data.current" :total="data.total">

    </BasicTable>
    <Modal
      v-model="userRoleTreeShow"
      :title="'用户['+modalTreeName+']授权'">
      <span>角色列表：</span>
      <Tree ref="UserRoleTree" :data="treeData" show-checkbox></Tree>
      <div slot="footer">
        <Button type="text" size="large" @click="userRoleTreeShow=false">取消</Button>
        <Button type="primary" size="large" @click="submitChange()">提交</Button>
      </div>
    </Modal>
  </div>
</template>
<script>

    import BasicTable from "@/components/tables"
    import {getTableData, handleUserRole} from '@/api/user'
    import {queryRoleTree} from '@/api/role'

    export default {
        name: 'userManage',
        data() {
            return {
                treeData: [],
                columns: [{
                    title: '昵称',
                    key: 'nickname'
                }, {
                    title: '账号',
                    key: 'account'
                }, {
                    title: '电话',
                    key: 'phone'
                }, {
                    title: '邮箱',
                    key: 'email'
                }, {
                    title: '加入时间',
                    key: 'createDate'
                }, {
                    title: '锁定时间',
                    key: 'lockDate'
                }, {
                    title: '头像',
                    key: 'portrait',
                    render: (h, params) => {
                        return h('div', {
                            attrs: {
                                style: 'width: 40px;height: 40px;'
                            },
                        }, [
                            h('img', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                attrs: {
                                    src: params.row.portrait, style: 'width: 40px;height: 40px;border-radius: 2px;'
                                },
                                style: {},
                            }),
                        ]);
                    }
                }, {
                    title: '操作',
                    key: 'action',
                    width: 200,
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                style: {
                                    marginRight: '3px'
                                },
                                on: {
                                    'click': () => {
                                        this.openTree(params.row);
                                    }
                                }
                            }, '用户授权')
                        ])
                    }
                }],
                data: {
                    records: [],
                    size: 10,
                    current: 1,
                    total: 0,
                    pages: 1
                },
                q: '', // 输入框,
                modalTreeName: '',
                userRoleTreeShow: false,
                userId: ''
            }
        },
        components: {BasicTable},
        methods: {
            handleSearch() {
                this.getData();
            },
            submitChange() {
                const checkedNodes = this.$refs.UserRoleTree.getCheckedNodes();
                handleUserRole({
                    id: this.userId,
                    ids: checkedNodes.map(s => s.id)
                }).then(res => {
                    if (res.code !== 200) {
                        this.$Message.error(res.data.msg);
                        return;
                    }
                    this.userRoleTreeShow = false;
                }).catch(err => {

                })
            },
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
            openTree(params) {
                this.modalTreeName = params.nickname;
                this.userId = params.id;
                this.userRoleTreeShow = true;
                queryRoleTree({userId: params.id}).then(res => {
                    if (res.code !== 200) {
                        this.$Message.error(res.data.msg);
                        return;
                    }
                    this.treeData = res.data.data;
                }).catch(err => {
                    console.log(err);
                })
            }
        },
        mounted() {
            this.getData();
        }
    }
</script>
<style scoped>

</style>
