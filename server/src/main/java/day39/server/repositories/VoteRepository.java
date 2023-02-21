package day39.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import static day39.server.configs.AppConfig.REDIS_LIKE;

import java.util.HashMap;
import java.util.Map;;

@Repository
public class VoteRepository {
    
    @Autowired
    @Qualifier(REDIS_LIKE)
    private RedisTemplate<String,String> template;

    public void intialise(String postId){
        HashOperations<String, String, String> voteMap = template.opsForHash();
        //redis doesnt have integer, only string representation of integer
		voteMap.put(postId, "like", "0");
		voteMap.put(postId, "dislike", "0");
    }

	public Map<String, Integer> getVotes(String postId) {
        HashOperations<String, String, String> voteMap = template.opsForHash();
		Map<String, Integer> votes = new HashMap<>();
		votes.put("like", Integer.parseInt(voteMap.get(postId, "like")));
		votes.put("dislike", Integer.parseInt(voteMap.get(postId, "dislike")));
		return votes;
	}
    
    public void like(String postId) {
        increment(postId, "like");
    }

    public void dislike(String postId) {
        increment(postId, "dislike");
    }

	public void increment(String postId, String key) {
		HashOperations<String, String, String> voteMap = template.opsForHash();
		voteMap.increment(postId, key, 1);
	}
}
