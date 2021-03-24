package com.example.toyproject1.repository

import com.example.toyproject1.domain.Member
import org.springframework.jdbc.datasource.DataSourceUtils
import java.lang.Exception
import java.lang.IllegalStateException
import java.sql.*
import javax.sql.DataSource
import java.sql.SQLException





class JdbcMemberRepository : MemberRepository {

    private val dataSource : DataSource

                                                //DB랑 커넥팅하면 스프링부트가 DB소스를 만듬. 이를 주입해줌. (received_dataSource)
    constructor(received_dataSource: DataSource){ //received_dataSource : 주입받을 DB소스.
        this.dataSource = received_dataSource
        //dataSource.getConnection() //데이터베이스와 연결 가능.->
    }

    private fun getConnection() : Connection {  //DB데이터소스와의 커넥션을 가져옴.(SpringFramework 쓸때 디비는 꼭 'DataSourceUtils'로 접근해야함.)
        return DataSourceUtils.getConnection(dataSource)
    }

    private fun close(connection : Connection?, pstmt: PreparedStatement?, rs : ResultSet?){
        try {
            if (rs != null) {
                rs.close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        try {
            if (pstmt != null) {
                pstmt.close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        try {
            if (connection != null) {
                close(connection)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    @kotlin.jvm.Throws(SQLException::class)
    private fun close(connection: Connection) {
        DataSourceUtils.releaseConnection(connection, dataSource)   //DB데이터소스와의 커넥션을 끊어줌.(SpringFramework 쓸때 디비는 꼭 'DataSourceUtils'로 접근해야함.)
    }

    override fun save(member: Member): Member {
        /*
        val connection : Connection = dataSource.getConnection() //DB랑 커넥션해줌.
        val pstmt : PreparedStatement = connection.prepareStatement(sql)

        pstmt.setString(1, member.name)
        pstmt.executeUpdate()
        */
        val sql : String  = "insert into member(name) values(?)" //?에 parameter binding할 것임. /*Insert into '(테이블이름)(테이블요소) '  'values(넣을값) '

        var connection : Connection? = null //DB와의 커넥팅 상태
        var pstmt : PreparedStatement? = null
        var rs : ResultSet? = null  //결과를 받아옴.

        try {
            connection = getConnection()
            pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) //connection이 되었을때, pstmt준비.
            pstmt.setString(1, member.name) //(중요) 위의 sql변수의 첫번째 물음표의 값을 지정해줌. => (sql) 결과는 디븨의 'name'컬럼에 member.name이라는 value가 들어감.

            pstmt.executeUpdate()   //디비에 실제로 해당쿼리('sql')가 날라감.
            rs = pstmt.generatedKeys //디비를 넣으면서 생성한 key를 반환해줌. (Statement.RETURN_GENERATED_KEYS)

            if (rs.next()){ //위의 키의 다음커서인 '값' 있으면
                member.id = rs.getLong(1)   //그 '값'을 꺼내옴.
            }
            else { //해당 키의 값이 없을 경우
                throw SQLException("id 조회 실패") // Exception 처리해줌.
            }
            return member                                                      //id랑 name이 저장된 member (어따씀?)!!!!!!!!!!!!!!
        }
        catch(e : Exception){
            throw IllegalStateException(e)
        }
        finally{
            close( connection,pstmt,rs) //자원들 release 해주어야함. -> DB커넥션의 경우 네트워크와 연결하는 것이기 때문에 쓰고 나면 바로 끊어야함.
        }                                                            //아니면 계속 쌓이다보면 엄청난 네트워크 장애를 초래하게 됨.

    }


    override fun findById(id: Long): Member? {
        val sql : String  = "select * from member where id=?"   //이때 member는 테이블 이름이다.

        var connection : Connection? = null
        var pstmt : PreparedStatement? = null
        var rs : ResultSet? = null

        try {
            connection = getConnection()
            pstmt = connection.prepareStatement(sql)
            pstmt.setLong(1, id)

            rs =  pstmt.executeQuery() //(SELECT구문) 쿼리의 결과인 'ResultSet'의 객체의 값을 반환함.

            if (rs.next()){ //위의 키의 다음커서인 '값' 있으면
                var member : Member = Member()
                member.id = rs.getLong("id")
                member.name = rs.getString("name")
                return member                                       //member 테이블에서 select된 값을 출력해줌.
            }
            else {
                return null   }                                      //member 테이블에 해당 값이 없으므로 null을 반환.
        }
        catch(e : Exception){
            throw IllegalStateException(e)
        }
        finally{
            close( connection,pstmt,rs) //자원들 release 해주어야함. -> DB커넥션의 경우 네트워크와 연결하는 것이기 때문에 쓰고 나면 바로 끊어야함.
        }

    }


    override fun findByName(name: String): Member? {
        val sql : String  = "select * from member where name=?"

        var connection : Connection? = null
        var pstmt : PreparedStatement? = null
        var rs : ResultSet? = null

        try {
            connection = getConnection()
            pstmt = connection.prepareStatement(sql)
            pstmt.setString(1, name)

            rs =  pstmt.executeQuery() //(SELECT구문) 쿼리의 결과인 'ResultSet'의 객체의 값을 반환함.

            if (rs.next()){ //위의 키의 다음커서인 '값' 있으면
                var member : Member = Member()
                member.id = rs.getLong("id")
                member.name = rs.getString("name")
                return member                                       //member 테이블에서 select된 값을 출력해줌.
            }
            else {
                return null   }                                      //member 테이블에 해당 값이 없으므로 null을 반환.
        }
        catch(e : Exception){
            throw IllegalStateException(e)
        }
        finally{
            close( connection,pstmt,rs) //자원들 release 해주어야함. -> DB커넥션의 경우 네트워크와 연결하는 것이기 때문에 쓰고 나면 바로 끊어야함.
        }
    }

    override fun findAll(): List<Member> {

        val sql : String  = "select * from member "   // 걍 모든 걸 select함.

        var connection : Connection? = null
        var pstmt : PreparedStatement? = null
        var rs : ResultSet? = null

        try {
            connection = getConnection()
            pstmt = connection.prepareStatement(sql)

            rs =  pstmt.executeQuery() //(SELECT구문) 쿼리의 결과인 'ResultSet'의 객체의 값을 반환함.

            var members : MutableList<Member> = ArrayList()

            while (rs.next()){ //값이 있을때까지 뽑아냄. (중요)
                var member : Member = Member()
                member.id = rs.getLong("id")
                member.name = rs.getString("name")
                members.add(member)
            }
            return members
        }
        catch(e : Exception){
            throw IllegalStateException(e)
        }
        finally{
            close( connection,pstmt,rs) //자원들 release 해주어야함. -> DB커넥션의 경우 네트워크와 연결하는 것이기 때문에 쓰고 나면 바로 끊어야함.
        }
    }

}