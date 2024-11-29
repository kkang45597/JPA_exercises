package com.mingi.jpaexs.qentity;

import com.mingi.jpaexs.entity.Member;
import com.mingi.jpaexs.entity.Team;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QTeam is a Querydsl query type for Team
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeam extends EntityPathBase<Team> {

    private static final long serialVersionUID = 1612997350L;

    public static final QTeam team = new QTeam("team");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Member, QMember> memberList = this.<Member, QMember>createList("memberList", Member.class, QMember.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public QTeam(String variable) {
        super(Team.class, forVariable(variable));
    }

    public QTeam(Path<? extends Team> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeam(PathMetadata metadata) {
        super(Team.class, metadata);
    }

}

