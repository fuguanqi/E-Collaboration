package com.ec.mapper;

import com.ec.entity.Mail;
import com.ec.entity.MailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail
     *
     * @mbggenerated Thu Mar 29 08:58:49 CST 2018
     */
    int countByExample(MailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail
     *
     * @mbggenerated Thu Mar 29 08:58:49 CST 2018
     */
    int deleteByExample(MailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail
     *
     * @mbggenerated Thu Mar 29 08:58:49 CST 2018
     */
    int deleteByPrimaryKey(Integer mailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail
     *
     * @mbggenerated Thu Mar 29 08:58:49 CST 2018
     */
    int insert(Mail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail
     *
     * @mbggenerated Thu Mar 29 08:58:49 CST 2018
     */
    int insertSelective(Mail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail
     *
     * @mbggenerated Thu Mar 29 08:58:49 CST 2018
     */
    List<Mail> selectByExample(MailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail
     *
     * @mbggenerated Thu Mar 29 08:58:49 CST 2018
     */
    Mail selectByPrimaryKey(Integer mailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail
     *
     * @mbggenerated Thu Mar 29 08:58:49 CST 2018
     */
    int updateByExampleSelective(@Param("record") Mail record, @Param("example") MailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail
     *
     * @mbggenerated Thu Mar 29 08:58:49 CST 2018
     */
    int updateByExample(@Param("record") Mail record, @Param("example") MailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail
     *
     * @mbggenerated Thu Mar 29 08:58:49 CST 2018
     */
    int updateByPrimaryKeySelective(Mail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail
     *
     * @mbggenerated Thu Mar 29 08:58:49 CST 2018
     */
    int updateByPrimaryKey(Mail record);
}