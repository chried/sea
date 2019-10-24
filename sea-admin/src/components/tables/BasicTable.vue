<template>
  <div>
    <div class="table-function-button" v-if="showButton">
      <Button @click="add">添加</Button>
      &nbsp;
      <Button @click="remove">删除</Button>
    </div>
    <Table border ref="selection" :columns="columns" :data="data" @on-row-click='onRowClick'></Table>
    <div class="table-bottom">
      <div class="table-bottom-left" v-if="showButton">
        <Button @click="handleSelectAll(true)">设置全选</Button>
        &nbsp;
        <Button @click="handleSelectAll(false)">取消全选</Button>
      </div>

      <div class="table-bottom-right">
        <Page :total="total" :page-size="size" @on-change="changePage" @on-page-size-change="_nowPageSize"
              :current="current"
              show-total show-sizer show-elevator/>
      </div>
    </div>
  </div>
</template>
<script>
    import './BasicTable.less'

    export default {
        name: 'basic-table',
        props: {
            // 展示列
            columns: {
                type: Array,
                default() {
                    return []
                }
            },
            data: Array,
            size: Number, // 每页展示.
            total: Number, // 总数.
            current: Number, // 当前页.
            showButton: Boolean,
            'on-next-page': Function,
            'on-delete': Function,
            'on-add': Function,
            'on-page-size': Function
        },
        data() {
            return {}
        },
        methods: {
            add() {
                this.$emit('on-add');
            },
            remove() {
                const selectIds = this.$refs.selection.getSelection().map(s => s.id);
                if (selectIds.length === 0) {
                    this.$Message.error('请至少选择一个');
                    return;
                }
                this.$emit('on-delete', selectIds);
            },
            onRowClick(index) {
            },
            changePage(next) {
                this.$emit('on-next-page', next);
            },
            handleSelectAll(status) {
                this.$refs.selection.selectAll(status);
            },
            _nowPageSize(index) {
                this.$emit('on-page-size', index);
            },

        }
    }
</script>
