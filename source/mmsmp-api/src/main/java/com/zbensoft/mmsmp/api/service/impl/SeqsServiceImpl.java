package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.SeqsService;
import com.zbensoft.mmsmp.db.domain.Seqs;
import com.zbensoft.mmsmp.db.mapper.SeqsMapper;

@Service
public class SeqsServiceImpl implements SeqsService {
	@Autowired
	SeqsMapper seqsMapper;

	@Override
	public int deleteByPrimaryKey(String seqsCode) {
		return seqsMapper.deleteByPrimaryKey(seqsCode);
	}

	@Override
	public int insert(Seqs record) {
		return seqsMapper.insert(record);
	}

	@Override
	public int insertSelective(Seqs record) {
		return seqsMapper.insertSelective(record);
	}

	@Override
	public Seqs selectByPrimaryKey(String seqsCode) {
		return seqsMapper.selectByPrimaryKey(seqsCode);
	}

	@Override
	public int updateByPrimaryKeySelective(Seqs record) {
		return seqsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Seqs record) {
		return seqsMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Seqs> selectPage(Seqs record) {
		return seqsMapper.selectPage(record);
	}

	@Override
	public int count(Seqs record) {
		return seqsMapper.count(record);
	}

	@Override
	public int updateBySeqNumber(Seqs updateBean) {
		return seqsMapper.updateBySeqNumber(updateBean);
	}

	


}
