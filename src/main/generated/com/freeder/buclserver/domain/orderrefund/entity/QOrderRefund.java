package com.freeder.buclserver.domain.orderrefund.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderRefund is a Querydsl query type for OrderRefund
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderRefund extends EntityPathBase<OrderRefund> {

    private static final long serialVersionUID = 1661881300L;

    public static final QOrderRefund orderRefund = new QOrderRefund("orderRefund");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final DateTimePath<java.time.LocalDateTime> completedAt = createDateTime("completedAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> refundAmount = createNumber("refundAmount", Integer.class);

    public final NumberPath<Integer> rewardUseAmount = createNumber("rewardUseAmount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QOrderRefund(String variable) {
        super(OrderRefund.class, forVariable(variable));
    }

    public QOrderRefund(Path<? extends OrderRefund> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderRefund(PathMetadata metadata) {
        super(OrderRefund.class, metadata);
    }

}

