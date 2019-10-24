<template>
  <div>
    <div class="search-con search-con-top">
      <Input clearable placeholder="权限名称/权限标识" class="search-input" v-model="q"/>
      <Button @click="handleSearch" class="search-btn" type="primary">
        <Icon type="search"/>&nbsp;&nbsp;搜索
      </Button>
    </div>
    <BasicTable :columns="columns" :data="data.records" :size="data.size" :current="data.current" :total="data.total"
                @on-next-page="getNextPage" @on-add="addPermission" @on-delete="deletePermissions"
                @on-page-size="handlePageSize"
                :showButton="true">

    </BasicTable>
    <Modal
      v-model="PermissionFormShow"
      title="Common Modal dialog box title">
      <Form ref="formInline" :model="formInline" :rules="ruleInline" label-position="left">
        <input type="hidden" v-model="formInline.id"/>
        <FormItem label="权限名称" prop="name">
          <Input type="text" v-model="formInline.name" placeholder="请输入权限名称">
          </Input>
        </FormItem>
        <FormItem label="权限标志" prop="sign">
          <Input type="text" v-model="formInline.sign" placeholder="请输入权限标志">
          </Input>
        </FormItem>
        <FormItem label="控制连接" prop="url">
          <Input type="text" v-model="formInline.url" placeholder="请输入控制连接">
          </Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="text" size="large" @click="cancel">取消</Button>
        <Button type="primary" size="large" @click="submitForm('formInline')">确定</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
    import BasicTable from "@/components/tables"
    import {getTableData, addOrEditPermission, deletePermissions} from '@/api/permission'

    export default {
        data() {
            return {
                formInline: {
                    id: '',
                    name: '',
                    sign: '',
                    url: ''
                },
                ruleInline: {
                    name: [
                        {required: true, message: '请输入权限名称', trigger: 'blur'}
                    ],
                    sign: [
                        {required: true, message: '请输入权限标志', trigger: 'blur'}
                    ],
                    url: [
                        {required: true, message: '请输入控制连接', trigger: 'blur'}
                    ]
                },
                PermissionFormShow: false, // form是否显示.
                columns: [{
                    type: 'selection',
                    width: 60,
                    align: 'center'
                }, {
                    title: '权限名称',
                    key: 'name'
                }, {
                    title: '权限标志',
                    key: 'sign'
                }, {
                    title: '控制连接',
                    key: 'url'
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
                                        this.editPermission(params.row);
                                    }
                                }
                            }, '编辑'),
                            h('Poptip', {
                                props: {
                                    confirm: true,
                                    title: '您确定要删除吗?'
                                },
                                on: {
                                    'on-ok': () => {
                                        this.deletePermission(params.row.id);
                                    }
                                }
                            }, [
                                h('Button', '删除')
                            ])
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
                q: '' // 输入框
            }
        },
        components: {
            BasicTable
        },
        methods: {
            cancel() {
                this.PermissionFormShow = false;
            },
            submitForm(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        addOrEditPermission(this.formInline).then(res => {
                            if (res.code === 200) {
                                this.PermissionFormShow = false;
                                this.getData();
                                setTimeout(() => {
                                    this.$refs['formInline'].resetFields();
                                }, 500);
                            } else {
                                this.$Message.error(res.data.data);
                            }
                        }).catch(err => {
                            console.log(err);
                        });
                    } else {
                        this.$Message.error('请输入必填项！');
                        return;
                    }
                });
            },
            handleSearch() {
                this.getData();
            },
            getNextPage(next) {
                this.data.current = next;
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
            editPermission(params) {
                this.formInline = params;
                this.PermissionFormShow = true;
            },
            addPermission() {
                this.formInline = {};
                this.PermissionFormShow = true;
            },
            deletePermissions(ids) {
                if (ids.length === 0) {
                    this.$Message.error("请至少选择一个");
                    return;
                }
                deletePermissions(ids).then(res => {
                    if (res.code !== 200) {
                        this.$Message.error(res.data.msg);
                        return;
                    }
                    this.getData();
                }).catch(err => {
                    console.log(err);
                })
            },
            deletePermission(id) {
                this.deletePermissions([id]);
            },
            handlePageSize(index) {
                this.data.size = index;
                this.getData();
            }
        },
        mounted() {
            this.getData();
        }
    }
</script>
<style scoped>

</style>
