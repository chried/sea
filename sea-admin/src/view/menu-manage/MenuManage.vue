<template>
  <div>
    <div class="search-con search-con-top">
      <Input clearable placeholder="菜单名称/菜单编号" class="search-input" v-model="q"/>
      <Button @click="handleSearch" class="search-btn" type="primary">
        <Icon type="search"/>&nbsp;&nbsp;搜索
      </Button>
    </div>
    <BasicTable :columns="columns" :data="data.records" :size="data.size" :current="data.current" :total="data.total"
                @on-next-page="getNextPage" @on-add="addMenu" @on-delete="deleteMenus" @on-page-size="handlePageSize"
                :showButton="true">

    </BasicTable>
    <Modal
      v-model="menuFormShow"
      :title="formName+'菜单'">
      <Form ref="formInline" :model="formInline" :rules="ruleInline" label-position="left">
        <input type="hidden" v-model="formInline.id"/>
        <input type="hidden" v-model="formInline.parentId"/>
        <FormItem label="菜单名称" prop="name">
          <Input type="text" v-model="formInline.name" placeholder="请输入菜单名称">
          </Input>
        </FormItem>
        <FormItem label="菜单编号" prop="code">
          <Input type="text" v-model="formInline.code" placeholder="请输入菜单编号">
          </Input>
        </FormItem>
        <FormItem label="菜单连接" prop="url">
          <Input type="text" v-model="formInline.url" placeholder="请输入菜单连接">
          </Input>
        </FormItem>
        <FormItem label="菜单图标" prop="icon">
          <Input type="text" v-model="formInline.icon" placeholder="请输入菜单图标">
          </Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="text" size="large" @click="cancel">取消</Button>
        <Button type="primary" size="large" @click="submitForm('formInline')">确定</Button>
      </div>
    </Modal>
    <Modal
      v-model="childMenuFormShow"
      :width='800'
      :title="childFormName">
      <Button :style="{marginBottom:'3px'}" @click="addChildMenu">添加子菜单</Button>
      <Table :columns="childColumns" :data="childData"></Table>
      <div slot="footer">
        <Button type="text" size="large" @click="cancelChildMenu">关闭</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
    import BasicTable from "@/components/tables"
    import {getTableData, addOrEditMenu, deleteMenus, getChildTableData} from '@/api/menu'

    export default {
        name: 'menuManage',
        data() {
            return {
                menuFormShow: false,// form是否显示.
                childMenuFormShow: false, // 子菜单form是否显示.
                formInline: {
                    id: '',
                    parentId: '',
                    name: '',
                    code: '',
                    url: '',
                    icon: ''
                },
                ruleInline: {
                    name: [
                        {required: true, message: '请输入菜单名称', trigger: 'blur'}
                    ],
                    code: [
                        {required: true, message: '请输入菜单编号', trigger: 'blur'}
                    ],
                    url: [
                        {required: true, message: '请输入菜单连接', trigger: 'blur'}
                    ],
                    icon: [
                        {required: true, message: '请输入菜单图标', trigger: 'blur'}
                    ]
                },
                childData: [],
                childColumns: [{
                    title: '菜单名称',
                    key: 'name'
                }, {
                    title: '菜单编号',
                    key: 'code'
                }, {
                    title: '菜单连接',
                    key: 'url'
                }, {
                    title: '菜单图标',
                    key: 'icon'
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
                                        this.editMenu(params.row);
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
                                        this.deleteMenu(params.row.id);
                                    }
                                }
                            }, [
                                h('Button', '删除')
                            ])
                        ])
                    }
                }],
                columns: [{
                    type: 'selection',
                    width: 60,
                    align: 'center'
                }, {
                    title: '菜单名称',
                    key: 'name'
                }, {
                    title: '菜单编号',
                    key: 'code'
                }, {
                    title: '菜单连接',
                    key: 'url'
                }, {
                    title: '菜单图标',
                    key: 'icon'
                }, {
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                style: {
                                    marginRight: '3px'
                                },
                                on: {
                                    'click': () => {
                                        this.editMenu(params.row);
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
                                        this.deleteMenu(params.row.id);
                                    }
                                }
                            }, [
                                h('Button', {
                                    style: {
                                        marginRight: '3px'
                                    }
                                }, '删除')
                            ]),
                            h('Button', {
                                on: {
                                    'click': () => {
                                        this.childMenuFormShow = true;
                                        this.getChildData(params.row.id);
                                        this.formInline.parentId = params.row.id;
                                        this.childFormName = `查看[${params.row.name}]子菜单`;
                                    }
                                }
                            }, '子菜单管理'),
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
                childFormName: '',
            }
        },
        components: {BasicTable},
        methods: {
            addChildMenu() {
                this.menuFormShow = true;
            },
            cancelChildMenu() {
                this.childMenuFormShow = false;
            },
            cancel() {
                this.menuFormShow = false;
            },
            getNextPage(next) {
                this.data.current = next;
                this.getData();
            },
            submitForm(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        addOrEditMenu(this.formInline).then(res => {
                            if (res.code === 200) {
                                this.menuFormShow = false;
                                if (res.data.data.parentId) {
                                    this.getChildData(res.data.data.parentId);
                                } else {
                                    this.getData();
                                }
                                setTimeout(() => {
                                    this.$refs['formInline'].resetFields();
                                }, 500);
                            } else {
                                this.$Message.error(res.data.data);
                            }
                        }).catch(err => {
                            console.log(err);
                        })
                    } else {
                        this.$Message.error('请输入必填项！');
                    }
                });
            },
            deleteMenus(ids) {
                if (ids.length === 0) {
                    this.$Message.error("请至少选择一项");
                    return;
                }
                deleteMenus(ids).then(res => {
                    if (res.code !== 200) {
                        this.$Message.error(res.data.msg);
                        return;
                    }
                    if (this.formInline.parentId) {
                        this.getChildData(this.formInline.parentId);
                    } else {
                        this.getData();
                    }
                }).catch(err => {
                    console.log(err);
                })
            },
            deleteMenu(id) {
                this.deleteMenus([id])
            },
            editMenu(params) {
                this.formInline = params;
                this.menuFormShow = true;
            },
            addMenu() {
                this.menuFormShow = true;
                this.formInline = {};
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
            handlePageSize(index) {
                this.data.size = index;
                this.getData();
            },
            handleSearch() {
                this.getData();
            },
            getChildData(parentId) {
                getChildTableData({
                    parentId: parentId
                }).then(res => {
                    if (res.code != 200) {
                        this.$Message.error(res.data.msg);
                        return;
                    }
                    this.childData = res.data.data || {};
                }).catch(err => {
                    console.log(err);
                })
            }
        },
        computed: {
            formName() {
                return this.formInline.id ? '编辑' : '添加'
            }
        },
        mounted() {
            // 获取列表数据.
            this.getData();
        }
    }
</script>
<style scoped>

</style>
