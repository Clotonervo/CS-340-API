package models;

import java.util.Objects;

public class Follow {

    public User follower;
    public User followee;

    public Follow(User follower, User followee) {
        this.follower = follower;
        this.followee = followee;
    }

    public Follow(){}

    public User getFollower() {
        return follower;
    }

    public User getFollowee() {
        return followee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Follow that = (Follow) o;
        return follower.getAlias().equals(that.follower.getAlias()) &&
                followee.getAlias().equals(that.followee.getAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower, followee);
    }

    @Override
    public String toString() {
        return "Follow{" +
                "follower=" + follower.getAlias() +
                ", followee=" + followee.getAlias() +
                '}';
    }
}
