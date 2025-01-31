package com.kdy.nwtp.sns;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ReplyRepository extends CrudRepository<Reply, Integer>{
	public abstract List<Reply> findBySnsno(Sns s);
}
