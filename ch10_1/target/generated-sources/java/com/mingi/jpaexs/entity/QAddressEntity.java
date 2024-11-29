package com.mingi.jpaexs.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAddressEntity is a Querydsl query type for AddressEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAddressEntity extends EntityPathBase<AddressEntity> {

    private static final long serialVersionUID = 1357778574L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAddressEntity addressEntity = new QAddressEntity("addressEntity");

    public final QAddress address;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public QAddressEntity(String variable) {
        this(AddressEntity.class, forVariable(variable), INITS);
    }

    public QAddressEntity(Path<? extends AddressEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAddressEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAddressEntity(PathMetadata metadata, PathInits inits) {
        this(AddressEntity.class, metadata, inits);
    }

    public QAddressEntity(Class<? extends AddressEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

