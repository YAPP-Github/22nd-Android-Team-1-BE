CREATE TABLE products
(
	productId BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '상품ID',
    productName VARCHAR(50) NOT NULL COMMENT '상품이름',
    brandName VARCHAR(50) NOT NULL COMMENT '브랜드이름',
    price BIGINT(20) UNSIGNED NOT NULL COMMENT '가격',
    productCategoryType VARCHAR(20) NOT NULL COMMENT '상품카테고리타입',
    barcode VARCHAR(13) NOT NULL COMMENT '바코드',
    imageUrl TEXT NOT NULL COMMENT '이미지링크',
    valid TINYINT(1) NOT NULL COMMENT 'valid',
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시'
) CHARSET = utf8 COMMENT='상품목록';

create index products_idx01 on products (createdAt);
create index products_idx02 on products (updatedAt);
create index products_idx03 on products (barcode);

CREATE TABLE product_retailer_mappings
(
	productRetailerMappingId BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'productRetailerMappingId',
    productId BIGINT(20) UNSIGNED COMMENT '상품ID',
    retailerType VARCHAR(20) NOT NULL COMMENT '편의점종류',
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시'
) CHARSET = utf8 COMMENT='상품 편의점 mapping';

create index product_retailer_mappings_idx01 on product_retailer_mappings (createdAt);
create index product_retailer_mappings_idx02 on product_retailer_mappings (updatedAt);
create index product_retailer_mappings_idx03 on product_retailer_mappings (productId);
create index product_retailer_mappings_idx04 on product_retailer_mappings (retailerType);

CREATE TABLE pb_product_mappings
(
	pbProductMappingId BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'pbProductMappingId',
    productId BIGINT(20) UNSIGNED COMMENT '상품ID',
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시'
) CHARSET = utf8 COMMENT='pb 상품 mapping';

create index pb_product_mappings_idx01 on pb_product_mappings (createdAt);
create index pb_product_mappings_idx02 on pb_product_mappings (updatedAt);
create index pb_product_mappings_idx03 on pb_product_mappings (productId);

CREATE TABLE product_promotions
(
    productPromotionId BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'promotionItemId',
    productId BIGINT(20) UNSIGNED COMMENT '상품ID',
    promotionType VARCHAR(20) NOT NULL COMMENT '행사타입',
    retailerType VARCHAR(20) NOT NULL COMMENT '편의점종류',
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시'
)  CHARSET = utf8 COMMENT='행사 상품';

create index product_promotions_idx01 on product_promotions (createdAt);
create index product_promotions_idx02 on product_promotions (updatedAt);

CREATE TABLE members
(
    memberId BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'memberId',
    email VARCHAR(100) NOT NULL COMMENT '이메일',
    loginType VARCHAR(20) NOT NULL COMMENT '로그인타입',
    nickName VARCHAR(20) NOT NULL COMMENT '닉네임',
    memberStatus VARCHAR(20) NOT NULL COMMENT '회원상태',
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시'
) CHARSET = utf8 COMMENT='회원 정보';

create index members_idx01 on members (createdAt);
create index members_idx02 on members (updatedAt);
create index members_idx03 on members (email, loginType, memberStatus);

CREATE TABLE product_like_histories
(
    productLikeHistoryId BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'productLikeHistoryId',
    productId BIGINT(20) UNSIGNED COMMENT '상품ID',
    memberId BIGINT(20) UNSIGNED COMMENT '회원ID',
    likeType VARCHAR(20) NOT NULL COMMENT '추천상태',
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시'
) CHARSET = utf8 COMMENT='상품 좋아요 정보';

create index product_like_histories_idx01 on product_like_histories (createdAt);
create index product_like_histories_idx02 on product_like_histories (updatedAt);
create index product_like_histories_idx03 on product_like_histories (productId, memberId);

CREATE TABLE member_product_like_mappings
(
    memberProductLikeMappingId BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'memberProductLikeMappingId',
    productId BIGINT(20) UNSIGNED COMMENT '상품ID',
    memberId BIGINT(20) UNSIGNED COMMENT '회원ID',
    likeType VARCHAR(20) NOT NULL COMMENT '추천상태',
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시'
) CHARSET = utf8 COMMENT='유저 상품 좋아요 정보 mapping';

create index member_product_like_mappings_idx01 on member_product_like_mappings (createdAt);
create index member_product_like_mappings_idx02 on member_product_like_mappings (updatedAt);
create index member_product_like_mappings_idx03 on member_product_like_mappings (productId);
create index member_product_like_mappings_idx04 on member_product_like_mappings (memberId);

CREATE TABLE product_like_summaries
(
    productLikeSummaryId BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'productLikeSummaryId',
    productId BIGINT(20) UNSIGNED COMMENT '상품ID',
    likeCount BIGINT(20) UNSIGNED COMMENT '좋아요 개수',
    totalCount BIGINT(20) UNSIGNED COMMENT '전체 평가 개수',
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시'
) CHARSET = utf8 COMMENT='상품 좋아요 정보 요약';

create index product_like_summaries_idx01 on product_like_summaries (createdAt);
create index product_like_summaries_idx02 on product_like_summaries (updatedAt);
create index product_like_summaries_idx03 on product_like_summaries (productId);

CREATE TABLE product_comments
(
    productCommentId BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'productCommentId',
    productId BIGINT(20) UNSIGNED COMMENT '상품ID',
    memberId BIGINT(20) UNSIGNED COMMENT '회원ID',
    content VARCHAR(300) NOT NULL COMMENT '내용',
    valid TINYINT(1) NOT NULL COMMENT 'valid',
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시'
) CHARSET = utf8mb4 COMMENT='상품 리뷰 코멘트 mapping';

create index product_comments_idx01 on product_comments (createdAt);
create index product_comments_idx02 on product_comments (updatedAt);
create index product_comments_idx03 on product_comments (productId);
create index product_comments_idx04 on product_comments (memberId);

CREATE TABLE product_comment_likes
(
    productCommentLikeId BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'productCommentLikeId',
    productId BIGINT(20) UNSIGNED COMMENT '상품ID',
    memberId BIGINT(20) UNSIGNED COMMENT '회원ID',
    likeMemberId BIGINT(20) UNSIGNED COMMENT '좋아요한 회원ID',
    valid TINYINT(1) NOT NULL COMMENT 'valid',
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시'
) CHARSET = utf8 COMMENT='상품 리뷰 코멘트 mapping';

create index product_comment_likes_idx01 on product_comment_likes (createdAt);
create index product_comment_likes_idx02 on product_comment_likes (updatedAt);
create index product_comment_likes_idx03 on product_comment_likes (productId, memberId);
create index product_comment_likes_idx04 on product_comment_likes (likeMemberId);
