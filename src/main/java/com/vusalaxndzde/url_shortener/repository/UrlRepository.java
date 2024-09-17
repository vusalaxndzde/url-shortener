package com.vusalaxndzde.url_shortener.repository;

import com.vusalaxndzde.url_shortener.entity.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends CrudRepository<Url, String>
{
}
