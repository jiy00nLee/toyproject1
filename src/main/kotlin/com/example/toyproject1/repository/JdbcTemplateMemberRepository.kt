package com.example.toyproject1.repository

import com.example.toyproject1.domain.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import java.sql.ResultSet
import javax.sql.DataSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import java.util.*


class JdbcTemplateMemberRepository : MemberRepository {

    private var jdbcTemplate : JdbcTemplate

    @Autowired //근데 constructor(생성자)가 하나인 경우 @Autowired를 생략해두 댐.
    constructor(dataSource: DataSource){ //얘는 직접 jdbcTemplate을 injection 받을 수 X.
        this.jdbcTemplate = JdbcTemplate(dataSource)
    }

    override fun save(member: Member): Member {
        var jdbcInsert : SimpleJdbcInsert = SimpleJdbcInsert(jdbcTemplate) //Jdbc 템플릿을 인자로 받아오면 가지게 되는 jdbcinsert 객체이다.

        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id") // 자동으로 'id'를 생성하는 테이블을 만든다.
        val parameters: MutableMap<String, Any?> = HashMap()
        parameters["name"] = member.name
        val key = jdbcInsert.executeAndReturnKey(MapSqlParameterSource(parameters)) //테이블에 parameter을 넣고 이의 자동으로 생성된 키를 반환한다.
        member.id = key.toLong() //해당 키 또한 멤버 데이터에 넣어준다.
        return member
    }

    override fun findById(id: Long): Member? {
        var result : MutableList<Member?> = jdbcTemplate.query("select * from member where id =?", memberRowMapper(), id)
        return result.firstOrNull() //result.stream.findAny() -> 무작위로 하나 출력하고 싶음.(근데 지금은 무조건 처음꺼)
    }

    override fun findByName(name: String): Member? {
        var result : MutableList<Member?> = jdbcTemplate.query("select * from member where name =?", memberRowMapper(), name)
        return result.firstOrNull()
    }

    override fun findAll(): List<Member> {
        return jdbcTemplate.query("select * from member", memberRowMapper())
    }


    private fun memberRowMapper(): RowMapper<Member> { //리스트에서 가져온 값을 멤버로 묶어서 저장해줌.
        return RowMapper { rs: ResultSet, rowNum: Int -> //rs, rownum(input), member(output)
            val member = Member()
            member.id = rs.getLong("id")
            member.name = rs.getString("name")
            member
        }
    }
}