package com.mingi.jpaexs.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteFood is a Querydsl query type for FavoriteFood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavoriteFood extends EntityPathBase<FavoriteFood> {

    private static final long serialVersionUID = -1749376093L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteFood favoriteFood = new QFavoriteFood("favoriteFood");

    public final StringPath foodName = createString("foodName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public QFavoriteFood(String variable) {
        this(FavoriteFood.class, forVariable(variable), INITS);
    }

    public QFavoriteFood(Path<? extends FavoriteFood> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteFood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteFood(PathMetadata metadata, PathInits inits) {
        this(FavoriteFood.class, metadata, inits);
    }

    public QFavoriteFood(Class<? extends FavoriteFood> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

