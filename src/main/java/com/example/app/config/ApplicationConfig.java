package com.example.app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.app.filter.AuthFilter;

//このクラスは：
//
//バリデーション用の Validator とメッセージファイル(validation.properties) を設定
//
///items/* に対して ログインチェック用フィルター(AuthFilter) を登録
//
//という「アプリ全体の設定」をまとめたクラスになっています。



//このクラスは「設定クラス（コンフィグ）」です、という印。
//Spring が起動時にこのクラスを読み取り、@Bean メソッドなどを登録します。
@Configuration
//Spring MVC の設定をカスタマイズするためのインターフェース。
//WebMvcConfigurer を実装すると、バリデーションやフォーマットなどを上書きできるようになります。
public class ApplicationConfig implements WebMvcConfigurer{

	// WebMvcConfigurer の getValidator をオーバーライドして、
	// 「画面バリデーションに使う Validator を自分で用意します」という宣言。
	@Override
	public Validator getValidator() {
		
		// Java の var（ローカル変数型推論）で LocalValidatorFactoryBean 型の変数を作成。
		// LocalValidatorFactoryBean は、Bean Validation（@NotBlank など）用の Validator を生成するクラス。
		var validator = new LocalValidatorFactoryBean();
		
		// バリデーションメッセージを取得するときに使う MessageSource を指定。
		// ここでは、下の messageSource() メソッドで定義したものを使う。
		validator.setValidationMessageSource(messageSource());
		
		// 最終的に、この Validator を返す。
		// これが画面の入力チェック（@Valid + Errors）に使われる。
		return validator;
	}
	
	
	// バリデーションメッセージなどを管理する MessageSource の Bean 定義。
	// @Bean が付いているので、Spring コンテナに登録されて、他のところから注入して使える。
	@Bean
	MessageSource messageSource() {
		
		// こちらも var で ResourceBundleMessageSource のインスタンスを作成。
		// ResourceBundleMessageSource は、プロパティファイル（.properties）からメッセージを読む仕組み。
		var messageSource = new ResourceBundleMessageSource();
		
		// ベース名を "validation" に指定。
		// → classpath 上の validation.properties / validation_ja.properties などからメッセージを読む。
		//   例：validation.properties に wrong_id_or_pass=IDまたはパスワードが不正です など。
		messageSource.setBasename("validation");
		return messageSource;// 作成した MessageSource を返す。
	}
	
	
	// 認証フィルター(AuthFilter)を登録するための Bean 定義。
	// FilterRegistrationBean を使うと、「どのURLに対してこのフィルターを適用するか」を設定できる。
	@Bean
	FilterRegistrationBean<AuthFilter> authFilter(){
		
		// AuthFilter のインスタンスを元に FilterRegistrationBean を生成。
		// <AuthFilter> は、このフィルターが扱う型（ジェネリクス）。
		var bean = new FilterRegistrationBean<AuthFilter>(new AuthFilter());
		
		// このフィルターを適用するパスパターンを指定。
		// "/items/*" のすべてのURL（/items, /items/xxx, /items/xxx/yyy…）に対して AuthFilter が動く。
		bean.addUrlPatterns("/items/*");
		return bean;// 設定済みの FilterRegistrationBean を返して、Spring に登録してもらう。
	}
	
}
