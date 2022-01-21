.PHONY: clean build run dev-up

clean:
	gradle clean

build:
	gradle bootJar -p shop-platform-config
	gradle bootJar -p shop-platform-registry-eureka
	gradle bootJar -p shop-account

run:

dev-up:
	docker-compose -f docker-compose.dev.yml up

dev-image-clean:
	echo yes | docker-compose -f docker-compose.dev.yml rm
