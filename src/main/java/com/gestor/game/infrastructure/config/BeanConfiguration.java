package com.gestor.game.infrastructure.config;

import com.gestor.game.application.mappers.build.BuildMapper;
import com.gestor.game.application.mappers.character.CharacterMapper;
import com.gestor.game.application.mappers.game.GameMapper;
import com.gestor.game.application.mappers.item.ItemMapper;
import com.gestor.game.application.mappers.user.UserMapper;
import com.gestor.game.application.port.out.build.BuildRepositoryPort;
import com.gestor.game.application.port.out.character.CharacterRepositoryPort;
import com.gestor.game.application.port.out.game.GameRepositoryPort;
import com.gestor.game.application.port.out.item.ItemRepositoryPort;
import com.gestor.game.application.port.out.security.PasswordEncoderPort;
import com.gestor.game.application.port.out.user.UserRepositoryPort;
import com.gestor.game.application.usecase.build.CreateBuildUseCaseImpl;
import com.gestor.game.application.usecase.build.RetrieveBuildUseCaseImpl;
import com.gestor.game.application.usecase.character.CreateCharacterUseCaseImpl;
import com.gestor.game.application.usecase.character.DeleteCharacterUseCaseImpl;
import com.gestor.game.application.usecase.character.RetrieveCharacterUseCaseImpl;
import com.gestor.game.application.usecase.character.UpdateCharacterBuildUseCaseImpl;
import com.gestor.game.application.usecase.game.RegisterGameUseCaseImpl;
import com.gestor.game.application.usecase.game.RetrieveGameHistoryUseCaseImpl;
import com.gestor.game.application.usecase.item.CreateItemUseCaseImpl;
import com.gestor.game.application.usecase.item.DeleteItemUseCaseImpl;
import com.gestor.game.application.usecase.item.RetrieveItemUseCaseImpl;
import com.gestor.game.application.usecase.auth.LoginUseCaseImpl;
import com.gestor.game.application.usecase.user.CreateUserUseCaseImpl;
import com.gestor.game.application.usecase.user.RegisterUserUseCaseImpl;
import com.gestor.game.application.usecase.user.RetrieveUserUseCaseImpl;
import com.gestor.game.application.port.out.security.JwtTokenPort;
import com.gestor.game.infrastructure.adapters.persistence.build.BuildMapperJpa;
import com.gestor.game.infrastructure.adapters.security.BCryptPasswordEncoderAdapter;
import com.gestor.game.infrastructure.adapters.security.JwtTokenAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class BeanConfiguration {

    //LOS PTOS MAPPERS
    @Bean
    public UserMapper userMapper(){
        return new UserMapper();
    }
    @Bean
    public ItemMapper itemMapper(){
        return new ItemMapper();
    }
    @Bean
    public BuildMapper buildMapper(){
        return new BuildMapper(itemMapper());
    }
    @Bean
    public CharacterMapper characterMapper(){
        return new CharacterMapper(buildMapper());
    }
    @Bean
    public GameMapper gameMapper(){
        return new GameMapper();
    }
    // --- USER ---
    @Bean
    public PasswordEncoderPort passwordEncoderPort() {
        return new BCryptPasswordEncoderAdapter();
    }

    @Bean
    public JwtTokenPort jwtTokenPort(JwtProperties jwtProperties) {
        return new JwtTokenAdapter(jwtProperties);
    }

    @Bean
    public LoginUseCaseImpl loginUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            JwtTokenPort jwtTokenPort,
            UserMapper userMapper) {
        return new LoginUseCaseImpl(userRepositoryPort, passwordEncoderPort, jwtTokenPort, userMapper);
    }

    @Bean
    public CreateUserUseCaseImpl createUserUseCase(UserRepositoryPort userRepositoryPort, UserMapper userMapper) {
        return new CreateUserUseCaseImpl(userRepositoryPort,userMapper);
    }

    @Bean
    public RegisterUserUseCaseImpl registerUserUseCase(
            UserRepositoryPort userRepositoryPort,
            UserMapper userMapper,
            PasswordEncoderPort passwordEncoderPort) {
        return new RegisterUserUseCaseImpl(userRepositoryPort, userMapper, passwordEncoderPort);
    }

    @Bean
    public RetrieveUserUseCaseImpl retrieveUserUseCase(UserRepositoryPort userRepositoryPort, UserMapper userMapper) {
        return new RetrieveUserUseCaseImpl(userRepositoryPort,userMapper);
    }

    // --- ITEM ---
    @Bean
    public CreateItemUseCaseImpl createItemUseCase(ItemRepositoryPort itemRepositoryPort, ItemMapper itemMapper) {
        return new CreateItemUseCaseImpl(itemRepositoryPort, itemMapper);
    }

    @Bean
    public RetrieveItemUseCaseImpl retrieveItemUseCase(ItemRepositoryPort itemRepositoryPort, ItemMapper itemMapper) {
        return new RetrieveItemUseCaseImpl(itemRepositoryPort, itemMapper);
    }

    @Bean
    public DeleteItemUseCaseImpl deleteItemUseCase(ItemRepositoryPort itemRepositoryPort) {
        return new DeleteItemUseCaseImpl(itemRepositoryPort);
    }

    // --- BUILD ---
    /* NOTA: Si en tu CreateBuildUseCaseImpl inyectaste también el ItemRepositoryPort
       para buscar los ítems antes de crear la build, simplemente agrégalo al:
       public CreateBuildUseCaseImpl createBuildUseCase(BuildRepositoryPort buildPort, ItemRepositoryPort itemPort)
    */
    @Bean
    public CreateBuildUseCaseImpl createBuildUseCase(BuildRepositoryPort buildRepositoryPort, ItemRepositoryPort itemRepositoryPort, BuildMapper buildMapper) {
        return new CreateBuildUseCaseImpl(buildRepositoryPort, itemRepositoryPort, buildMapper);
    }

    @Bean
    public RetrieveBuildUseCaseImpl retrieveBuildUseCase(BuildRepositoryPort buildRepositoryPort, BuildMapper buildMapper) {
        return new RetrieveBuildUseCaseImpl(buildRepositoryPort,  buildMapper);
    }

    // --- CHARACTER ---
    @Bean
    public CreateCharacterUseCaseImpl createCharacterUseCase(CharacterRepositoryPort characterRepositoryPort, UserRepositoryPort userRepositoryPort, BuildRepositoryPort buildRepositoryPort, CharacterMapper characterMapper) {
        return new CreateCharacterUseCaseImpl(characterRepositoryPort,userRepositoryPort,buildRepositoryPort,characterMapper);
    }

    @Bean
    public RetrieveCharacterUseCaseImpl retrieveCharacterUseCase(CharacterRepositoryPort characterRepositoryPort, UserRepositoryPort userRepositoryPort, BuildRepositoryPort buildRepositoryPort, CharacterMapper characterMapper) {
        return new RetrieveCharacterUseCaseImpl(characterRepositoryPort,userRepositoryPort,characterMapper);
    }

    @Bean
    public UpdateCharacterBuildUseCaseImpl updateCharacterBuildUseCase(CharacterRepositoryPort characterRepositoryPort, BuildRepositoryPort buildRepositoryPort, CharacterMapper characterMapper) {
        return new UpdateCharacterBuildUseCaseImpl(characterRepositoryPort,buildRepositoryPort,characterMapper);
    }

    @Bean
    public DeleteCharacterUseCaseImpl deleteCharacterUseCase(CharacterRepositoryPort characterRepositoryPort) {
        return new DeleteCharacterUseCaseImpl(characterRepositoryPort);
    }

    // --- GAME ---
    @Bean
    public RegisterGameUseCaseImpl registerGameUseCase(GameRepositoryPort gameRepositoryPort, GameMapper gameMapper) {
        return new RegisterGameUseCaseImpl(gameRepositoryPort,gameMapper);
    }

    @Bean
    public RetrieveGameHistoryUseCaseImpl retrieveGameHistoryUseCase(GameRepositoryPort gameRepositoryPort,GameMapper gameMapper) {
        return new RetrieveGameHistoryUseCaseImpl(gameRepositoryPort,gameMapper);
    }
}
