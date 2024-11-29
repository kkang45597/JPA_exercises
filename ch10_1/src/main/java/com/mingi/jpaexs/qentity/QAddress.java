package com.mingi.jpaexs.qentity;

import com.mingi.jpaexs.entity.Address;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QAddress is a Querydsl query type for Address
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAddress extends BeanPath<Address> {

    private static final long serialVersionUID = 1001636555L;

    public static final QAddress address = new QAddress("address");

    public final StringPath city = createString("city");

    public final StringPath street = createString("street");

    public final StringPath zipCode = createString("zipCode");

    public QAddress(String variable) {
        super(Address.class, forVariable(variable));
    }

    public QAddress(Path<? extends Address> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddress(PathMetadata metadata) {
        super(Address.class, metadata);
    }

}

