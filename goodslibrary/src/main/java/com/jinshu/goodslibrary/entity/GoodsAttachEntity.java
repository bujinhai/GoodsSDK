package com.jinshu.goodslibrary.entity;

import java.util.List;

/**
 * Create on 2019/11/13 10:32 by bll
 */


public class GoodsAttachEntity {


    /**
     * data : {"total":1,"currentPage":1,"currentPgeNumber":1,"pageNumber":10,"totalPage":1,"hasNextPage":false,"rows":[{"attachmentID":"8a2f462a6d7be787016d7c2692b90087","name":"测试一下","documentTypeID":null,"documentTypeName":null,"fileType":null,"objectID":"8a2f462a5630aae1015630af51a8008d","objectName":"公司成立4周年大会","url":"http://admin.haoju.me:8082/kpbase//group/M00/4E/AA/A99E-1FDA-4771-903B-EED2CB423BEE.jpg","showType":1,"orderSeq":11,"createdtime":1569745965000,"createdtimeString":"2019-09-29 16:32:45","memberID":null,"memberName":null,"isValid":null}]}
     */

    private DataInfo data;

    public DataInfo getData() {
        return data;
    }

    public void setData(DataInfo data) {
        this.data = data;
    }

    public static class DataInfo {
        /**
         * total : 1
         * currentPage : 1
         * currentPgeNumber : 1
         * pageNumber : 10
         * totalPage : 1
         * hasNextPage : false
         * rows : [{"attachmentID":"8a2f462a6d7be787016d7c2692b90087","name":"测试一下","documentTypeID":null,"documentTypeName":null,"fileType":null,"objectID":"8a2f462a5630aae1015630af51a8008d","objectName":"公司成立4周年大会","url":"http://admin.haoju.me:8082/kpbase//group/M00/4E/AA/A99E-1FDA-4771-903B-EED2CB423BEE.jpg","showType":1,"orderSeq":11,"createdtime":1569745965000,"createdtimeString":"2019-09-29 16:32:45","memberID":null,"memberName":null,"isValid":null}]
         */

        private int total;
        private int currentPage;
        private int currentPgeNumber;
        private int pageNumber;
        private int totalPage;
        private boolean hasNextPage;
        private List<GoodsAttachInfo> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getCurrentPgeNumber() {
            return currentPgeNumber;
        }

        public void setCurrentPgeNumber(int currentPgeNumber) {
            this.currentPgeNumber = currentPgeNumber;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public List<GoodsAttachInfo> getRows() {
            return rows;
        }

        public void setRows(List<GoodsAttachInfo> rows) {
            this.rows = rows;
        }
    }
}
