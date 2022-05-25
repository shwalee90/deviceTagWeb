import React from "react";
import '../../css/Pagination.css';
import Pagination from "react-js-pagination";

const Paging = ({totalCount, postPerPage, pageRangeDisplayed, handlePageChange,page}) => {

  return (
    <Pagination
      activePage={page}
      itemsCountPerPage={postPerPage}
      totalItemsCount={totalCount}
      pageRangeDisplayed={pageRangeDisplayed}
      prevPageText={"‹"}
      nextPageText={"›"}
      onChange={handlePageChange}
    />
  );
};

export default Paging;
