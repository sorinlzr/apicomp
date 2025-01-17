//package net.pfsnc.apicomp.fh.grpc;
//
//import io.grpc.ServerBuilder;
//import lombok.Data;
//import lombok.Getter;
//import org.springframework.boot.convert.DataSizeUnit;
//import org.springframework.boot.convert.DurationUnit;
//import org.springframework.core.io.Resource;
//import org.springframework.util.unit.DataSize;
//import org.springframework.util.unit.DataUnit;
//
//import java.io.File;
//import java.io.InputStream;
//import java.time.Duration;
//import java.time.temporal.ChronoUnit;
//import java.util.concurrent.TimeUnit;
//
//
//@Data
////@ConfigurationProperties("grpc.server")
//@SuppressWarnings("javadoc")
//public class GrpcServerProperties {
//
//    /**
//     * A constant that defines, that the server should listen to any IPv4 and IPv6 address.
//     */
//    public static final String ANY_IP_ADDRESS = "*";
//
//    /**
//     * A constant that defines, that the server should listen to any IPv4 address.
//     */
//    public static final String ANY_IPv4_ADDRESS = "0.0.0.0";
//
//    /**
//     * A constant that defines, that the server should listen to any IPv6 address.
//     */
//    public static final String ANY_IPv6_ADDRESS = "::";
//
//    /**
//     * Bind address for the server. Defaults to {@link #ANY_IP_ADDRESS "*"}. Alternatively you can restrict this to
//     * {@link #ANY_IPv4_ADDRESS "0.0.0.0"} or {@link #ANY_IPv6_ADDRESS "::"}. Or restrict it to exactly one IP address.
//     * domain socket addresses/paths (Additional dependencies may be required).
//     *
//     * @param address The address to bind to.
//     * @return The address the server should bind to.
//     */
//    private String address = ANY_IP_ADDRESS;
//
//    /**
//     * Server port to listen on. Defaults to {@code 9090}. If set to {@code 0} a random available port will be selected
//     * and used. Use {@code -1} to disable the inter-process server (for example if you only want to use the in-process
//     * server).
//     *
//     *
//     * -- GETTER --
//     *  Gets the port the server should listen on. Defaults to
//     * . If set to
//     *  a random available port
//     *  will be selected and used.
//     *
//     @param port The port the server should listen on.
//     * @return The port the server will listen on.
//     * @return The server port to listen to.
//     *
//     *
//     */
//    @Getter
//    private int port = 9090;
//
//    /**
//     * The name of the in-process server. If not set, then the in process server won't be started.
//     *
//     * @param inProcessName The name of the in-process server.
//     * @return The name of the in-process server or null if isn't configured.
//     */
//    private String inProcessName;
//
//    /**
//     * The time to wait for the server to gracefully shutdown (completing all requests after the server started to
//     * shutdown). If set to a negative value, the server waits forever. If set to {@code 0} the server will force
//     * shutdown immediately. Defaults to {@code 30s}.
//     *
//     * @param gracefullShutdownTimeout The time to wait for a graceful shutdown.
//     * @return The time to wait for a graceful shutdown.
//     */
//    @DurationUnit(ChronoUnit.SECONDS)
//    private Duration shutdownGracePeriod = Duration.of(30, ChronoUnit.SECONDS);
//
//    /**
//     * Setting to enable keepAlive. Default to {@code false}.
//     *
//     * @param enableKeepAlive Whether keep alive should be enabled.
//     * @return True, if keep alive should be enabled. False otherwise.
//     */
//    private boolean enableKeepAlive = false;
//
//    /**
//     * The default delay before we send a keepAlives. Defaults to {@code 2h}. Default unit {@link ChronoUnit#SECONDS
//     * SECONDS}.
//     *
//     * @see #setEnableKeepAlive(boolean)
//     * @see NettyServerBuilder#keepAliveTime(long, TimeUnit)
//     *
//     * @param keepAliveTime The new default delay before sending keepAlives.
//     * @return The default delay before sending keepAlives.
//     */
//    @DurationUnit(ChronoUnit.SECONDS)
//    private Duration keepAliveTime = Duration.of(2, ChronoUnit.HOURS);
//
//    /**
//     * The default timeout for a keepAlives ping request. Defaults to {@code 20s}. Default unit
//     * {@link ChronoUnit#SECONDS SECONDS}.
//     *
//     * @see #setEnableKeepAlive(boolean)
//     * @see NettyServerBuilder#keepAliveTimeout(long, TimeUnit)
//     *
//     * @param keepAliveTimeout Sets the default timeout for a keepAlives ping request.
//     * @return The default timeout for a keepAlives ping request.
//     */
//    @DurationUnit(ChronoUnit.SECONDS)
//    private Duration keepAliveTimeout = Duration.of(20, ChronoUnit.SECONDS);
//
//    /**
//     * Specify the most aggressive keep-alive time clients are permitted to configure. Defaults to {@code 5min}. Default
//     * unit {@link ChronoUnit#SECONDS SECONDS}.
//     *
//     * @see NettyServerBuilder#permitKeepAliveTime(long, TimeUnit)
//     *
//     * @param permitKeepAliveTime The most aggressive keep-alive time clients are permitted to configure.
//     * @return The most aggressive keep-alive time clients are permitted to configure.
//     */
//    @DurationUnit(ChronoUnit.SECONDS)
//    private Duration permitKeepAliveTime = Duration.of(5, ChronoUnit.MINUTES);
//
//    /**
//     * Whether clients are allowed to send keep-alive HTTP/2 PINGs even if there are no outstanding RPCs on the
//     * connection. Defaults to {@code false}.
//     *
//     * @see NettyServerBuilder#permitKeepAliveWithoutCalls(boolean)
//     *
//     * @param permitKeepAliveWithoutCalls Whether to allow clients to send keep-alive requests without calls.
//     * @return True, if clients are allowed to send keep-alive requests without calls. False otherwise.
//     */
//    @DurationUnit(ChronoUnit.SECONDS)
//    private boolean permitKeepAliveWithoutCalls = false;
//
//    /**
//     * Specify a max connection idle time. Defaults to disabled. Default unit {@link ChronoUnit#SECONDS SECONDS}.
//     *
//     * @see NettyServerBuilder#maxConnectionIdle(long, TimeUnit)
//     *
//     * @param maxConnectionIdle The max connection idle time.
//     * @return The max connection idle time.
//     */
//    @DurationUnit(ChronoUnit.SECONDS)
//    private Duration maxConnectionIdle = null;
//
//    /**
//     * Specify a max connection age. Defaults to disabled. Default unit {@link ChronoUnit#SECONDS SECONDS}.
//     *
//     * @see NettyServerBuilder#maxConnectionAge(long, TimeUnit)
//     *
//     * @param maxConnectionAge The max connection age.
//     * @return The max connection age.
//     */
//    @DurationUnit(ChronoUnit.SECONDS)
//    private Duration maxConnectionAge = null;
//
//    /**
//     * Specify a grace time for the graceful max connection age termination. Defaults to disabled. Default unit
//     * {@link ChronoUnit#SECONDS SECONDS}.
//     *
//     * @see NettyServerBuilder#maxConnectionAgeGrace(long, TimeUnit)
//     *
//     * @param maxConnectionAgeGrace The max connection age grace time.
//     * @return The max connection age grace time.
//     */
//    @DurationUnit(ChronoUnit.SECONDS)
//    private Duration maxConnectionAgeGrace = null;
//
//    /**
//     * The maximum message size allowed to be received by the server. If not set ({@code null}) then
//     * {@link GrpcUtil#DEFAULT_MAX_MESSAGE_SIZE gRPC's default} should be used.
//     *
//     * @return The maximum message size allowed.
//     */
//    @DataSizeUnit(DataUnit.BYTES)
//    private DataSize maxInboundMessageSize = null;
//
//    /**
//     * The maximum size of metadata allowed to be received. If not set ({@code null}) then
//     * {@link GrpcUtil#DEFAULT_MAX_HEADER_LIST_SIZE gRPC's default} should be used.
//     *
//     * @return The maximum metadata size allowed.
//     */
//    @DataSizeUnit(DataUnit.BYTES)
//    private DataSize maxInboundMetadataSize = null;
//
//    /**
//     * Whether gRPC health service is enabled or not. Defaults to {@code true}.
//     *
//     * @param healthServiceEnabled Whether gRPC health service is enabled.
//     * @return True, if the health service is enabled. False otherwise.
//     */
//    private boolean healthServiceEnabled = true;
//
//    /**
//     * Whether proto reflection service is enabled or not. Defaults to {@code true}.
//     *
//     * @param reflectionServiceEnabled Whether gRPC reflection service is enabled.
//     * @return True, if the reflection service is enabled. False otherwise.
//     */
//    private boolean reflectionServiceEnabled = true;
//
//    /**
//     * Security options for transport security. Defaults to disabled. We strongly recommend to enable this though.
//     *
//     * @return The security options for transport security.
//     */
//    private final Security security = new Security();
//
//    /**
//     * The security configuration for the gRPC server.
//     */
//    @Data
//    public static class Security {
//
//
//        /**
//         * Flag that controls whether transport security is used. Defaults to {@code false}. If {@code true}, either set
//         * {@link #certificateChain} and {@link #privateKey}, or {@link #keyStore}.
//         *
//         * @param enabled Whether transport security should be enabled.
//         * @return True, if transport security should be enabled. False otherwise.
//         */
//        private boolean enabled = false;
//
//        /**
//         * The resource containing the SSL certificate chain. Use is combination with {@link #privateKey}. Cannot be
//         * used in conjunction with {@link #keyStore}.
//         *
//         * @see GrpcSslContexts#forServer(InputStream, InputStream, String)
//         *
//         * @param certificateChain The certificate chain resource.
//         * @return The certificate chain resource or null.
//         */
//        private Resource certificateChain = null;
//
//        /**
//         * The resource containing the private key. Use in combination with {@link #certificateChain}. Cannot be used in
//         * conjunction with {@link #keyStore}.
//         *
//         * @see GrpcSslContexts#forServer(InputStream, InputStream, String)
//         *
//         * @param privateKey The private key resource.
//         * @return The private key resource or null.
//         */
//        private Resource privateKey = null;
//
//        /**
//         * Password for the private key. Use is combination with {@link #privateKey}.
//         *
//         * @see GrpcSslContexts#forServer(File, File, String)
//         *
//         * @param privateKeyPassword The password for the private key.
//         * @return The password for the private key or null.
//         */
//        private String privateKeyPassword = null;
//
//
//        /**
//         * The resource containing the key store. Cannot be used in conjunction with {@link #privateKey}.
//         *
//         * @param keyStore The key store resource.
//         * @return The key store resource or null.
//         */
//        private Resource keyStore = null;
//
//        /**
//         * Password for the key store. Use is combination with {@link #keyStore}.
//         *
//         * @param keyStorePassword The password for the key store.
//         * @return The password for the key store or null.
//         */
//        private String keyStorePassword = null;
//    }
//
//    /**
//     * Sets the maximum message size allowed to be received by the server. If not set ({@code null}) then it will
//     * default to {@link GrpcUtil#DEFAULT_MAX_MESSAGE_SIZE gRPC's default}. If set to {@code -1} then it will use the
//     * highest possible limit (not recommended).
//     *
//     * @param maxInboundMessageSize The new maximum size allowed for incoming messages. {@code -1} for max possible.
//     *        Null to use the gRPC's default.
//     *
//     * @see ServerBuilder#maxInboundMessageSize(int)
//     */
//    public void setMaxInboundMessageSize(final DataSize maxInboundMessageSize) {
//        if (maxInboundMessageSize == null || maxInboundMessageSize.toBytes() >= 0) {
//            this.maxInboundMessageSize = maxInboundMessageSize;
//        } else if (maxInboundMessageSize.toBytes() == -1) {
//            this.maxInboundMessageSize = DataSize.ofBytes(Integer.MAX_VALUE);
//        } else {
//            throw new IllegalArgumentException("Unsupported maxInboundMessageSize: " + maxInboundMessageSize);
//        }
//    }
//
//    /**
//     * Sets the maximum metadata size allowed to be received by the server. If not set ({@code null}) then it will
//     * default to {@link GrpcUtil#DEFAULT_MAX_HEADER_LIST_SIZE gRPC's default}. If set to {@code -1} then it will use
//     * the highest possible limit (not recommended).
//     *
//     * @param maxInboundMetadataSize The new maximum size allowed for incoming metadata. {@code -1} for max possible.
//     *        Null to use the gRPC's default.
//     *
//     * @see ServerBuilder#maxInboundMetadataSize(int)
//     */
//    public void setMaxInboundMetadataSize(final DataSize maxInboundMetadataSize) {
//        if (maxInboundMetadataSize == null || maxInboundMetadataSize.toBytes() >= 0) {
//            this.maxInboundMetadataSize = maxInboundMetadataSize;
//        } else if (maxInboundMetadataSize.toBytes() == -1) {
//            this.maxInboundMetadataSize = DataSize.ofBytes(Integer.MAX_VALUE);
//        } else {
//            throw new IllegalArgumentException("Unsupported maxInboundMetadataSize: " + maxInboundMetadataSize);
//        }
//    }
//
//}