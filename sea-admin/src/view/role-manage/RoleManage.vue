<template>
  <div>
    <div class="search-con search-con-top">
      <Input clearable placeholder="角色名称/角色标识" class="search-input" v-model="q"/>
      <Button @click="handleSearch" class="search-btn" type="primary">
        <Icon type="search"/>&nbsp;&nbsp;搜索
      </Button>
    </div>
    <BasicTable :columns="columns" :data="data.records" :size="data.size" :current="data.current" :total="data.total"
                @on-next-page="getNextPage" @on-add="addRole" @on-delete="deleteRoles" @on-page-size="handlePageSize"
                :showButton="true">

    </BasicTable>
    <Modal
      v-model="roleFormShow"
      :title="formName+'角色'">
      <Form ref="formInline" :model="formInline" :rules="ruleInline" label-position="left">
        <input type="hidden" v-model="formInline.id"/>
        <FormItem label="角色名称" prop="name">
          <Input type="text" v-model="formInline.name" placeholder="请输入角色名称">
          </Input>
        </FormItem>
        <FormItem label="角色标志" prop="sign">
          <Input type="text" v-model="formInline.sign" placeholder="请输入角色标志">
          </Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="text" size="large" @click="cancel">取消</Button>
        <Button type="primary" size="large" @click="submitForm('formInline')">确定</Button>
      </div>
    </Modal>
    <Modal
      v-model="rolePermissionTreeShow"
      :title="'角色['+roleName+']授权权限'">
      <Tree ref="RolePermissionTree" :data="permissionTreeData" show-checkbox></Tree>
      <div slot="footer">
        <Button type="text" size="large" @click="rolePermissionTreeShow=false">取消</Button>
        <Button type="primary" size="large" @click="submitRolePermissionTree">确定</Button>
      </div>
    </Modal>
    <Modal
      v-model="roleMenuTreeShow"
      :title="'角色['+roleName+']授权菜单'">
      <Tree ref="RoleMenuTree" :data="menuTreeData" show-checkbox></Tree>
      <div slot="footer">
        <Button type="text" size="large" @click="roleMenuTreeShow=false">取消</Button>
        <Button type="primary" size="large" @click="submitRoleMenuTree">确定</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
    import BasicTable from "@/components/tables"
    import {getTableData, addOrEditRole, deleteRoles, handleRolePermission, handleRoleMenu} from '@/api/role'
    import {queryMenuTree} from '@/api/menu'
    import {queryPermissionTree} from '@/api/permission'

    export default {
        data() {
            return {
                permissionTreeData: [],
                menuTreeData: [],
                rolePermissionTreeShow: false,
                roleMenuTreeShow: false,
                roleName: '',
                roleId: '',
                formInline: {
                    id: '',
                    name: '',
                    sign: ''
                },
                ruleInline: {
                    name: [
                        {required: true, message: '请输入角色名称', trigger: 'blur'}
                    ],
                    sign: [
                        {required: true, message: '请输入角色标志', trigger: 'blur'}
                    ]
                },
                roleFormShow: false, // form是否显示.
                columns: [{
                    type: 'selection',
                    width: 60,
                    align: 'center'
                }, {
                    title: '角色名称',
                    key: 'name'
                }, {
                    title: '角色标志',
                    key: 'sign'
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
                                        this.editRole(params.row);
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
                                        this.deleteRole(params.row.id);
                                    }
                                }
                            }, [
                                h('Button', {
                                    style: {
                                        marginRight: '3px'
                                    },
                                }, '删除')
                            ]),
                            h('Button', {
                                style: {
                                    marginRight: '3px'
                                },
                                on: {
                                    'click': () => {
                                        this.handleRolePermission(params.row);
                                    }
                                }
                            }, '授权权限'), ,
                            h('Button', {
                                on: {
                                    'click': () => {
                                        this.handleRoleMenu(params.row);
                                    }
                                }
                            }, '授权菜单'),
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
        computed: {
            formName() {
                return this.formInline.id ? '编辑' : '添加';
            }
        },
        methods: {
            cancel() {
                this.roleFormShow = false;
            },
            submitForm(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        addOrEditRole(this.formInline).then(res => {
                            if (res.code === 200) {
                                this.roleFormShow = false;
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
            editRole(params) {
                this.formInline = params;
                this.roleFormShow = true;
            },
            addRole() {
                this.formInline = {};
                this.roleFormShow = true;
            },
            deleteRoles(ids) {
                if (ids.length === 0) {
                    this.$Message.error("请至少选择一个");
                    return;
                }
                deleteRoles(ids).then(res => {
                    if (res.code !== 200) {
                        this.$Message.error(res.data.msg);
                        return;
                    }
                    this.getData();
                }).catch(err => {
                    console.log(err);
                })
            },
            deleteRole(id) {
                this.deleteRoles([id]);
            },
            handlePageSize(index) {
                this.data.size = index;
                this.getData();
            },
            handleRolePermission(params) {
                this.roleName = params.name;
                this.roleId = params.id;
                this.rolePermissionTreeShow = true;
                queryPermissionTree({roleId: params.id}).then(res => {
                    if (res.code !== 200) {
                        this.$Message.error(res.data.msg);
                        return;
                    }
                    this.permissionTreeData = res.data.data;
                }).catch(err => {
                    console.log(err);
                })
            },
            handleRoleMenu(params) {
                this.roleName = params.name;
                this.roleId = params.id;
                this.roleMenuTreeShow = true;
                queryMenuTree({roleId: params.id}).then(res => {
                    if (res.code !== 200) {
                        this.$Message.error(res.data.msg);
                        return;
                    }
                    this.menuTreeData = res.data.data;
                }).catch(err => {
                    console.log(err);
                })
            },
            submitRolePermissionTree() {
                const checkedNodes = this.$refs.RolePermissionTree.getCheckedAndIndeterminateNodes();
                handleRolePermission({
                    id: this.roleId,
                    ids: checkedNodes.map(s => s.id)
                }).then(res => {
                    if (res.code !== 200) {
                        this.$Message.error(res.data.msg);
                        return;
                    }
                    this.rolePermissionTreeShow = false;
                }).catch(err => {
                    console.log(err);
                })
            },
            submitRoleMenuTree() {
                const checkedNodes = this.$refs.RoleMenuTree.getCheckedAndIndeterminateNodes();
                handleRoleMenu({
                    id: this.roleId,
                    ids: checkedNodes.map(s => s.id)
                }).then(res => {
                    if (res.code !== 200) {
                        this.$Message.error(res.data.msg);
                        return;
                    }
                    this.roleMenuTreeShow = false;
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
