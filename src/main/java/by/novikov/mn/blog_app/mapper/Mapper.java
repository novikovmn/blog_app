package by.novikov.mn.blog_app.mapper;

public interface Mapper<E, D> {

    D mapToDto(E entity);
    E mapToEntity(D dto);

}
