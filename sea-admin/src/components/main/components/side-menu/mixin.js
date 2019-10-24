import CommonIcon from '_c/common-icon'
import {showTitle} from '@/libs/util'

export default {
  components: {
    CommonIcon
  },
  methods: {
    showTitle(item) {
      return showTitle(item, this)
    },
    showChildren(item) {
      return item.children
    },
    getNameOrHref(item, children0) {
      return item.code;
    }
  }
}
